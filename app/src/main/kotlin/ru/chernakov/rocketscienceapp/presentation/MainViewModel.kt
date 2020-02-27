package ru.chernakov.rocketscienceapp.presentation

import com.google.firebase.auth.FirebaseAuth
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.util.lifecycle.SingleLiveEvent

class MainViewModel(firebaseAuth: FirebaseAuth) : BaseViewModel() {
    val selectedNavigationItemEvent = SingleLiveEvent<Int>()
    val isFirstLaunchWithUser = selectedNavigationItemEvent.value == null && firebaseAuth.currentUser != null

    fun setSelectedNavigationItem(itemId: Int): Boolean {
        if (selectedNavigationItemEvent.value != itemId) selectedNavigationItemEvent.value = itemId

        return true
    }
}