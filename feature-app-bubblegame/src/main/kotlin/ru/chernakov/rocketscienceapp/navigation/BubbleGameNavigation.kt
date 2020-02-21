package ru.chernakov.rocketscienceapp.navigation

import ru.chernakov.rocketscienceapp.presentation.host.BubbleGameHostFragment

interface BubbleGameNavigation {
    fun fromBubbleGameToAppFeatures()
    fun openBubbleGameMenu(hostFragment: BubbleGameHostFragment)
    fun startBubbleGame(hostFragment: BubbleGameHostFragment)
    fun openBubbleGameResult(hostFragment: BubbleGameHostFragment)
}