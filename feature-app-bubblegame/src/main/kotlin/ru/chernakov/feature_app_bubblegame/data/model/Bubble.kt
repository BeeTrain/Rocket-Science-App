package ru.chernakov.feature_app_bubblegame.data.model

data class Bubble(
    val circle: Circle,
    val position: BubblePosition,
    val pointerIds: MutableSet<Int> = mutableSetOf()
) {

    fun hasPointers(): Boolean {
        return pointerIds.isNotEmpty()
    }
}