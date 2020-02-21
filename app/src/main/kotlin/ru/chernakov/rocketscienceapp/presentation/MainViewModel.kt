package ru.chernakov.rocketscienceapp.presentation

import com.google.firebase.auth.FirebaseAuth
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.util.lifecycle.SingleLiveEvent

class MainViewModel(private val firebaseAuth: FirebaseAuth) : BaseViewModel() {
    val selectedNavigationItemEvent = SingleLiveEvent<Int>()

    fun getUser() = firebaseAuth.currentUser

    fun setSelectedNavigationItem(itemId: Int) {
        if (selectedNavigationItemEvent.value != itemId) selectedNavigationItemEvent.value = itemId
    }
}