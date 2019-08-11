package ru.chernakov.rocketscienceapp.presentation.ui.register

import android.text.Editable
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import ru.chernakov.rocketscienceapp.presentation.ui.base.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.util.lifecycle.SingleLiveEvent

class RegisterViewModel(private val firebaseAuth: FirebaseAuth) : BaseViewModel() {

    var registerSuccessEvent = SingleLiveEvent<Boolean>()
    var registerErrorEvent = SingleLiveEvent<Exception>()

    fun isEmailValid(email: Editable) = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isPasswordValid(password: Editable) = password.length >= 8

    fun isPasswordConfirmValid(password: Editable, confirm: Editable) = password.toString() == confirm.toString()

    fun registerUser(email: String, password: String) {
        loading.postValue(true)
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    loading.postValue(false)
                    registerSuccessEvent.postValue(it.isSuccessful)
                }
                .addOnFailureListener {
                    loading.postValue(false)
                    registerErrorEvent.postValue(it)
                }
    }
}