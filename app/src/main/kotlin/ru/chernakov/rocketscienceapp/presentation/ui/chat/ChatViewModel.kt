package ru.chernakov.rocketscienceapp.presentation.ui.chat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import ru.chernakov.rocketscienceapp.data.model.chat.Message
import ru.chernakov.rocketscienceapp.domain.interactor.chat.PrepareMessageInteractor
import ru.chernakov.rocketscienceapp.presentation.ui.base.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.util.FIREBASE_CHILD_MESSAGES
import ru.chernakov.rocketscienceapp.util.INTENT_TYPE_IMAGE
import ru.chernakov.rocketscienceapp.util.lifecycle.SingleLiveEvent

class ChatViewModel(private val prepareMessageInteractor: PrepareMessageInteractor) : BaseViewModel() {
    val userLiveData = MutableLiveData<FirebaseUser>()
    val analyticsLiveData = MutableLiveData<FirebaseAnalytics>()
    val databaseReferenceData = MutableLiveData<DatabaseReference>()

    var userNotLoggedEvent = SingleLiveEvent<Nothing>()

    lateinit var storageReference: StorageReference

    private fun putImageOnStorage(activity: Activity, uri: Uri, key: String) {
        databaseReferenceData.value?.let { database ->
            val storageReference = storageReference
                .child(key)
                .child(uri.lastPathSegment ?: "")
            storageReference.putFile(uri).addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    task.result!!.metadata!!.reference!!.downloadUrl.addOnCompleteListener(activity) {
                        if (task.isSuccessful) {
                            val message = prepareMessage(imageUrl = task.result.toString())
                            database.child(FIREBASE_CHILD_MESSAGES).child(key).setValue(message)
                        }
                    }
                }
            }
        }
    }

    fun firebaseLogin() {
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            userNotLoggedEvent.call()
        } else {
            userLiveData.postValue(firebaseUser)
        }
    }

    fun onUserLogged(context: Context, user: FirebaseUser) {
        analyticsLiveData.postValue(FirebaseAnalytics.getInstance(context))
        databaseReferenceData.postValue(FirebaseDatabase.getInstance().reference)
        storageReference = FirebaseStorage.getInstance()
            .getReference(user.uid)
    }

    fun preparePhotoIntent(): Intent {
        return Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = INTENT_TYPE_IMAGE
        }
    }

    fun prepareMessage(text: String = "", imageUrl: String = ""): Message {
        var message = Message()
        userLiveData.value?.let {
            message = prepareMessageInteractor.prepareMessage(it, text, imageUrl)
        }
        return message
    }

    fun sendMessage(text: String) {
        databaseReferenceData.value?.child(FIREBASE_CHILD_MESSAGES)?.push()?.setValue(prepareMessage(text))
    }

    fun sendImage(activity: Activity, message: Message, uri: Uri) {
        databaseReferenceData.value?.let {
            it.child(FIREBASE_CHILD_MESSAGES).push().setValue(message) { error, database ->
                if (error == null) {
                    val key = database.key ?: ""
                    FirebaseStorage.getInstance()
                        .getReference(userLiveData.value!!.uid)
                        .child(key)
                        .child(uri.lastPathSegment ?: "")
                    putImageOnStorage(activity, uri, key)
                }
            }
        }
    }
}