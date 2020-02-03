package ru.chernakov.rocketscienceapp.presentation

import com.google.firebase.auth.FirebaseAuth
import ru.chernakov.core_base.util.lifecycle.SingleLiveEvent
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel

class MainViewModel(private val firebaseAuth: FirebaseAuth) : BaseViewModel() {
    val selectedNavigationItemEvent = SingleLiveEvent<Int>()

    fun getUser() = firebaseAuth.currentUser

    fun setSelectedNavigationItem(itemId: Int) {
        if (selectedNavigationItemEvent.value != itemId) selectedNavigationItemEvent.value = itemId
    }
}