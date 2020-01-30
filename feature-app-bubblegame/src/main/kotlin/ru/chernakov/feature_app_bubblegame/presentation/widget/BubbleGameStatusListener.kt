package ru.chernakov.feature_app_bubblegame.presentation.widget

import ru.chernakov.feature_app_bubblegame.data.GameStatus

interface BubbleGameStatusListener {
    fun onGameStatusChanged(gameStatus: GameStatus)
}