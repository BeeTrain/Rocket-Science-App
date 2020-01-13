package ru.chernakov.feature_app_bubblegame.util

import android.os.SystemClock
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import ru.chernakov.feature_app_bubblegame.data.GameSpeed
import ru.chernakov.feature_app_bubblegame.data.model.Circle
import ru.chernakov.feature_app_bubblegame.domain.BubbleGameInteractor

class BubblePositionUtil {
    private lateinit var circles: List<Circle>
    private lateinit var gameSpeed: GameSpeed
    private lateinit var circlesStates: MutableList<GameState>
    private var screenWidth = 0

    private val interpolator: Interpolator = LinearInterpolator()

    fun setGame(bubbleGameInteractor: BubbleGameInteractor) {
        circles = bubbleGameInteractor.circles
        gameSpeed = bubbleGameInteractor.speed
        screenWidth = bubbleGameInteractor.screenWidth.toInt()
        circlesStates = MutableList(circles.size) { index ->
            GameState(SystemClock.elapsedRealtime(), circles[index].x, 1)
        }
    }

    fun update() {
        val speedTime = gameSpeed.timeMills

        for ((i, item) in circles.withIndex()) {
            val radius = item.radius
            val state = circlesStates[i]
            val currStateTime = SystemClock.elapsedRealtime() - state.startTime
            val coef = currStateTime / speedTime.toFloat()
            val interpolated = interpolator.getInterpolation(coef)

            val posDelta = screenWidth * interpolated
            var newX =
                state.startX + posDelta * state.direction + if (state.direction == -1) screenWidth - radius else radius

            if (newX < radius) {
                newX = radius
                state.reset(1)
            } else if (newX > screenWidth - radius) {
                newX = screenWidth.toFloat() - radius
                state.reset(-1)
            }

            item.x = newX
        }
    }

    private data class GameState(var startTime: Long, var startX: Float, var direction: Int) {
        fun reset(direction: Int) {
            this.direction = direction
            startTime = SystemClock.elapsedRealtime()
            startX = 0f
        }
    }
}