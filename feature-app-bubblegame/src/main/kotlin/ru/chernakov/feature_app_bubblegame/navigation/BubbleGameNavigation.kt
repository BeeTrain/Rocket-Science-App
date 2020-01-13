package ru.chernakov.feature_app_bubblegame.navigation

import ru.chernakov.feature_app_bubblegame.presentation.host.BubbleGameHostFragment

interface BubbleGameNavigation {
    fun fromBubbleGameToFlow()
    fun openBubbleGameMenu(hostFragment: BubbleGameHostFragment)
    fun startBubbleGame(hostFragment: BubbleGameHostFragment)
    fun openBubbleGameResult(hostFragment: BubbleGameHostFragment)
}