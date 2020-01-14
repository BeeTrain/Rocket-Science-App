package ru.chernakov.feature_app_bubblegame.presentation.menu.settings

import ru.chernakov.feature_app_bubblegame.data.GameSpeed
import ru.chernakov.feature_app_bubblegame.data.GameTime

data class GameSettingsModel(
    var bubbleCount: Int = 3,
    var gameSpeed: GameSpeed = GameSpeed.STATIC,
    var gameTime: GameTime = GameTime.EASY
)