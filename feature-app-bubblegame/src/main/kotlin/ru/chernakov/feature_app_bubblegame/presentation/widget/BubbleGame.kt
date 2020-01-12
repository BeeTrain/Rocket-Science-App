package ru.chernakov.feature_app_bubblegame.presentation.widget

import ru.chernakov.feature_app_bubblegame.data.GameSpeed
import ru.chernakov.feature_app_bubblegame.data.GameSpeed.STATIC
import ru.chernakov.feature_app_bubblegame.data.GameTime
import ru.chernakov.feature_app_bubblegame.data.GameTime.EASY

class BubbleGame(private val params: Params) {


    class Params(
        var width: Int = 0,
        var height: Int = 0,
        var circleCount: Int = MIN_CIRCLES_COUNT,
        var gameSpeed: GameSpeed = STATIC,
        var gameTime: GameTime = EASY
    )

    companion object {
        private const val MAX_CIRCLES_COUNT = 10
        private const val MIN_CIRCLES_COUNT = 3
    }
}