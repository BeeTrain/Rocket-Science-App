package ru.chernakov.feature_splash.presentation

import com.google.firebase.auth.FirebaseAuth
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel

class SplashViewModel(private val firebaseAuth: FirebaseAuth) : BaseViewModel() {

    fun getUser() = firebaseAuth.currentUser
}