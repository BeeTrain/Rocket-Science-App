package ru.chernakov.feature_app_bubblegame.presentation.widget

interface BubbleGameStateListener {
    fun onSettingsSet()
    fun onScreenParamsSet()
    fun onSettingsReset()
}