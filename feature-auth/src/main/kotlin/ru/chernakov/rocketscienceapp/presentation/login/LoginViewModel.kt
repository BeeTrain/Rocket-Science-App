package ru.chernakov.rocketscienceapp.presentation.login

import android.os.CountDownTimer
import android.text.Editable
import android.util.Patterns
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.util.lifecycle.SingleLiveEvent

class LoginViewModel(
    val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient
) : BaseViewModel() {

    var signInEvent = SingleLiveEvent<Boolean>()
    var resetPasswordEvent = SingleLiveEvent<Boolean>()
    var authErrorEvent = SingleLiveEvent<Exception>()
    var resendEmailTimerEvent = SingleLiveEvent<Long>()
    var resendEmailAccessEvent = SingleLiveEvent<Boolean>()

    private var resetPasswordTimer: CountDownTimer? = null

    private fun createResetPassTimer(): CountDownTimer {
        return object : CountDownTimer(RESEND_EMAIL_DELAY, RESEND_EMAIL_TIMER_TICK) {
            override fun onTick(millisUntilFinished: Long) {
                resendEmailTimerEvent.value = millisUntilFinished
            }

            override fun onFinish() {
                resendEmailAccessEvent.value = true
                resetPasswordTimer = null
            }
        }
    }

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
        resetPasswordTimer = createResetPassTimer().start()
        resendEmailAccessEvent.value = false
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
        private const val RESEND_EMAIL_DELAY = 60000L
        private const val RESEND_EMAIL_TIMER_TICK = 1000L
    }
}