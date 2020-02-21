package ru.chernakov.rocketscienceapp.data

enum class GameSpeed(val timeMills: Long) {
    STATIC(Long.MAX_VALUE),
    LOW(SPEED_LOW),
    MEDIUM(SPEED_MEDIUM),
    HIGH(SPEED_HIGH)
}

private const val SPEED_LOW = 10_000L
private const val SPEED_MEDIUM = 6_000L
private const val SPEED_HIGH = 2_000L