package ru.chernakov.feature_app_bubblegame.game.game.logic

import android.os.SystemClock
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import ru.chernakov.feature_app_bubblegame.data.GameSpeed
import ru.chernakov.feature_app_bubblegame.data.model.Circle
import ru.chernakov.feature_app_bubblegame.game.game.Game

internal class CirclePositionCalculator constructor(game: Game) {
    private val circles: List<Circle> = game.circles
    private val gameSpeed: GameSpeed = game.speed
    private val interpolator: Interpolator = LinearInterpolator()
    private val circlesStates: MutableList<State> by lazy {
        MutableList(circles.size, { index -> State(SystemClock.elapsedRealtime(), circles[index].x, 1) })
    }

    private val screenWidth = game.screenWidth

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
}

private data class State(var startTime: Long, var startX: Float, var direction: Int) {
    fun reset(direction: Int) {
        this.direction = direction
        startTime = SystemClock.elapsedRealtime()
        startX = 0f
    }
}