package ru.chernakov.rocketscienceapp.presentation.ui.profile

import com.google.firebase.auth.FirebaseAuth
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel

class ProfileViewModel(private val firebaseAuth: FirebaseAuth) : BaseViewModel() {

    fun getUser() = firebaseAuth.currentUser
}