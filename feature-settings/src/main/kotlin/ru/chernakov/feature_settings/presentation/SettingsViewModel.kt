package ru.chernakov.feature_settings.presentation

import com.google.firebase.auth.FirebaseAuth
import ru.chernakov.core_base.util.lifecycle.SingleLiveEvent
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel

class SettingsViewModel(private val firebaseAuth: FirebaseAuth) : BaseViewModel() {

    var logoutEvent = SingleLiveEvent<Nothing>()

    fun logoutUser() {
        firebaseAuth.signOut()
        logoutEvent.call()
    }
}