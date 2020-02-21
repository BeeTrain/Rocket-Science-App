package ru.chernakov.rocketscienceapp.presentation.menu.settings

import ru.chernakov.rocketscienceapp.data.GameSpeed
import ru.chernakov.rocketscienceapp.data.GameTime

data class GameSettingsModel(
    var bubbleCount: Int = 3,
    var gameSpeed: GameSpeed = GameSpeed.STATIC,
    var gameTime: GameTime = GameTime.EASY
)