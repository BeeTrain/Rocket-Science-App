package ru.chernakov.rocketscienceapp.data

enum class GameTime(val timeMs: Long) {
    EASY(TIME_EASY),
    MEDIUM(TIME_MEDIUM),
    HARD(TIME_HARD)
}

private const val TIME_EASY = 10_000L
private const val TIME_MEDIUM = 7_000L
private const val TIME_HARD = 4_000L