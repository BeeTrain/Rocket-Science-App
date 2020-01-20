package ru.chernakov.feature_splash.presentation

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import ru.chernakov.core_base.util.lifecycle.SingleLiveEvent
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel

class SplashViewModel(private val firebaseAuth: FirebaseAuth) : BaseViewModel() {

    fun getUser() = firebaseAuth.currentUser
}