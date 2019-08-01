package ru.chernakov.rocketscienceapp.presentation.ui.login

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import ru.chernakov.rocketscienceapp.presentation.ui.base.viewmodel.BaseViewModel

class LoginViewModel(
    val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient
) : BaseViewModel() {

    fun getGoogleSignInIntent() = googleSignInClient.signInIntent
}