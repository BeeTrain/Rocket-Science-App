package ru.chernakov.feature_appfeatures.data.model

data class AppFeature(
    var id: Long,
    var name: String
) {

    companion object {
        const val BUBBLE_GAME_ID = 0L
    }
}