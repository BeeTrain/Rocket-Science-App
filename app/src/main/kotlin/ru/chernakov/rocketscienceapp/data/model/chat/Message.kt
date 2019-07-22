package ru.chernakov.rocketscienceapp.data.model.chat

data class Message(
    var id: String= "",
    var name: String = "",
    var photoUrl: String = "",
    var imageUrl: String = "",
    var text: String = ""
){
    companion object {
        const val ANONYMUS = "anonymus"
    }
}