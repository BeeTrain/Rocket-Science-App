package ru.chernakov.rocketscienceapp.data.model

data class Bubble(
    val circle: Circle,
    val position: BubblePosition,
    val pointerIds: MutableSet<Int> = mutableSetOf()
) {

    fun hasPointers(): Boolean {
        return pointerIds.isNotEmpty()
    }
}