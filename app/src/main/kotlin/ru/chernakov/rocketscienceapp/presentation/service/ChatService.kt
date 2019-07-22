package ru.chernakov.rocketscienceapp.presentation.service

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService

class ChatService : FirebaseMessagingService() {

    override fun onNewToken(token: String?) {
        FirebaseMessaging.getInstance().subscribeToTopic(MESSAGE_CHANNEL)
    }

    companion object {
        const val MESSAGE_CHANNEL = "message_channel"
    }
}