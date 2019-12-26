package ru.chernakov.feature_login.presentation.presentation

import android.text.Editable
import android.util.Patterns
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import ru.chernakov.core_base.util.lifecycle.SingleLiveEvent
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel

class LoginViewModel(
    val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient
) : BaseViewModel() {

    var signInEvent = SingleLiveEvent<Boolean>()
    var resetPasswordEvent = SingleLiveEvent<Boolean>()
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

    fun resetPassword(email: String) {
        loading.postValue(true)
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                loading.postValue(false)
                resetPasswordEvent.postValue(it.isSuccessful)
            }
    }

    fun isEmailValid(email: Editable) =
        email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()

    fun isPasswordValid(password: Editable) = password.trim().length >= PASSWORD_MIN_LENGTH

    companion object {
        private const val PASSWORD_MIN_LENGTH = 8
    }
}