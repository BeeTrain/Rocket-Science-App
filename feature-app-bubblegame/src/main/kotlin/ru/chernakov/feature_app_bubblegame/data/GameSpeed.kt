package ru.chernakov.feature_app_bubblegame.data

enum class GameSpeed(val timeMills: Long) {
    STATIC(Long.MAX_VALUE),
    LOW(10_000),
    MEDIUM(6_000),
    HIGH(2_000)
}