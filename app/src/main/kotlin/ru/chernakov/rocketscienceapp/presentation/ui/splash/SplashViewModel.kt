package ru.chernakov.rocketscienceapp.presentation.ui.splash

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ru.chernakov.rocketscienceapp.presentation.ui.base.viewmodel.BaseViewModel

class SplashViewModel : BaseViewModel() {

    val userLivaData = MutableLiveData<FirebaseUser?>()

    fun getUser(firebaseAuth: FirebaseAuth) {
        val auth= firebaseAuth.currentUser
        userLivaData.postValue(auth)
    }
}