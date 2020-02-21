package ru.chernakov.rocketscienceapp.presentation.widget

import ru.chernakov.rocketscienceapp.data.GameStatus

interface BubbleGameStatusListener {
    fun onGameStatusChanged(gameStatus: GameStatus)
}