package ru.chernakov.feature_profile.presentation

import com.google.firebase.auth.FirebaseAuth
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel

class ProfileViewModel(private val firebaseAuth: FirebaseAuth) : BaseViewModel() {

    fun getUser() = firebaseAuth.currentUser
}