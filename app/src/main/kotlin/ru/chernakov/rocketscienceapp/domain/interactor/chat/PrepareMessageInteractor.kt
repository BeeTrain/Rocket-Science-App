package ru.chernakov.rocketscienceapp.domain.interactor.chat

import com.google.firebase.auth.FirebaseUser
import ru.chernakov.rocketscienceapp.data.model.chat.Message

class PrepareMessageInteractor {

    fun prepareMessage(user: FirebaseUser, text: String = "", imageUrl: String = ""): Message {
        return Message(
            user.uid,
            user.displayName ?: Message.ANONYMUS,
            user.photoUrl?.toString() ?: "",
            imageUrl,
            text
        )
    }
}