package ru.chernakov.rocketscienceapp.presentation.ui.chat

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_chat.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.data.model.chat.Message
import ru.chernakov.rocketscienceapp.extension.android.widget.addTextChangedListener
import ru.chernakov.rocketscienceapp.presentation.ui.base.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.presentation.ui.base.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.chat.adapter.ChatAdapter
import ru.chernakov.rocketscienceapp.presentation.ui.login.LoginFragment
import ru.chernakov.rocketscienceapp.util.FIREBASE_CHILD_MESSAGES
import ru.chernakov.rocketscienceapp.util.lifecycle.SafeObserver


class ChatFragment : BaseFragment() {
    private val viewModel: ChatViewModel by viewModel()

    private var chatAdapter: ChatAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.firebaseLogin()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btSend.setOnClickListener { sendMessage() }
        btAdd.setOnClickListener { addPhoto() }
        etMessage.addTextChangedListener {
            onTextChanged { text, _, _, _ ->
                btSend.isEnabled = text.toString().trim().isNotEmpty()
            }
        }
        viewModel.userLiveData.observe(this, SafeObserver {
            context?.let { context -> viewModel.onUserLogged(context, it) }
        })
        viewModel.databaseReferenceData.observe(this, SafeObserver {
            initChatList(it)
        })
        viewModel.userNotLoggedEvent.observe(this, Observer {
            startFragment(LoginFragment.newInstance(), false)
        })
    }

    private fun initChatList(databaseReference: DatabaseReference) {
        val parser = SnapshotParser { snapshot ->
            val message = snapshot.getValue(Message::class.java)
            message?.id = snapshot.key!!
            message!!
        }
        val messagesRef = databaseReference.child(FIREBASE_CHILD_MESSAGES)
        val options = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(messagesRef, parser)
            .build()
        val chatAdapter = ChatAdapter(options)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.stackFromEnd = true
        chatAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(start: Int, count: Int) {
                super.onItemRangeInserted(start, count)
                val messageCount = chatAdapter.itemCount
                val lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (lastPosition == -1 || start >= messageCount - 1 && lastPosition == start - 1) {
                    rvChat.scrollToPosition(start)
                }
                plLoading.visibility = View.INVISIBLE
            }
        })
        rvChat.apply {
            layoutManager = linearLayoutManager
            adapter = chatAdapter
            chatAdapter.startListening()
        }
    }

    override fun onResume() {
        super.onResume()
        chatAdapter?.startListening()
    }

    override fun onPause() {
        chatAdapter?.stopListening()
        super.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val uri = data.data
                    val message = viewModel.prepareMessage()
                    viewModel.databaseReferenceData.value?.let {
                        it.child(FIREBASE_CHILD_MESSAGES).push().setValue(message) { error, database ->
                            if (error == null) {
                                val key = database.key
                                val storageReference = FirebaseStorage.getInstance()
                                    .getReference(viewModel.userLiveData.value!!.uid)
                                    .child(key ?: "")
                                    .child(uri?.lastPathSegment ?: "")
                                putImageOnStorage(storageReference, uri!!, key!!)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun addPhoto() {
        val intent = viewModel.preparePhotoIntent()
        startActivityForResult(intent, REQUEST_IMAGE)
    }

    private fun sendMessage() {
        viewModel.sendMessage(etMessage.text.toString())
        etMessage.text.clear()
    }

    private fun putImageOnStorage(storageReference: StorageReference, uri: Uri, key: String) {
        storageReference.putFile(uri).addOnCompleteListener(activity as Activity) { result ->
            if (result.isSuccessful) {
                result.result!!.metadata!!.reference!!.downloadUrl.addOnCompleteListener(activity as Activity) { task ->
                    if (task.isSuccessful) {
                        viewModel.databaseReferenceData.value?.let {
                            val message = viewModel.prepareMessage(imageUrl = task.result.toString())
                            it.child(FIREBASE_CHILD_MESSAGES).child(key).setValue(message)
                        }
                    }
                }
            }
        }
    }

    override fun getLayout(): Int = ru.chernakov.rocketscienceapp.R.layout.fragment_chat

    override fun obtainViewModel(): BaseViewModel = viewModel

    companion object {
        private const val REQUEST_IMAGE = 2
        private const val LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif"

        fun newInstance() = ChatFragment()
    }
}