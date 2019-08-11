package ru.chernakov.rocketscienceapp.presentation.ui.login

import android.text.Editable
import android.util.Patterns
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import ru.chernakov.rocketscienceapp.presentation.ui.base.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.util.lifecycle.SingleLiveEvent

class LoginViewModel(
        val firebaseAuth: FirebaseAuth,
        private val googleSignInClient: GoogleSignInClient
) : BaseViewModel() {

    var signInEvent = SingleLiveEvent<Boolean>()
    var authErrorEvent = SingleLiveEvent<Exception>()

    fun getGoogleSignInIntent() = googleSignInClient.signInIntent

    fun signInWithEmailAndPassword(email: String, password: String) {
        loading.postValue(true)
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    loading.postValue(false)
                    signInEvent.postValue(it.isSuccessful)
                }
                .addOnFailureListener {
                    loading.postValue(false)
                    authErrorEvent.postValue(it)
                }
    }

    fun isEmailValid(email: Editable) = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isPasswordValid(password: Editable) = password.length >= 8

}