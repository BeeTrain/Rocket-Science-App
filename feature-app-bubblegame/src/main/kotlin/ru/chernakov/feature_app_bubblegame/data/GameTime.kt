package ru.chernakov.feature_app_bubblegame.data

enum class GameTime(val timeMs: Long) {
    EASY(10_000),
    MEDIUM(7_000),
    HARD(4_000)
}