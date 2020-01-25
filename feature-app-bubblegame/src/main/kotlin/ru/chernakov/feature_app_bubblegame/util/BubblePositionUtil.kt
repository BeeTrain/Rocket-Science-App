package ru.chernakov.feature_app_bubblegame.util

import android.os.SystemClock
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import ru.chernakov.feature_app_bubblegame.data.GameSpeed
import ru.chernakov.feature_app_bubblegame.data.model.Bubble

class BubblePositionUtil {
    private val interpolator: Interpolator = LinearInterpolator()

    fun update(bubbles: List<Bubble>, gameSpeed: GameSpeed, screenWidth: Int): List<Bubble> {
        val updated = mutableListOf<Bubble>()
        for (bubble in bubbles) {
            val radius = bubble.circle.radius
            val state = bubble.position
            val currStateTime = SystemClock.elapsedRealtime() - state.startTime
            val coef = currStateTime / gameSpeed.timeMills.toFloat()
            val interpolated = interpolator.getInterpolation(coef)

            val posDelta = screenWidth * interpolated
            var newX =
                state.startX + posDelta * state.direction + if (state.direction == -1) screenWidth - radius else radius

            if (!bubble.hasPointers()) {
                if (newX < radius) {
                    newX = radius
                    state.reset(1)
                } else if (newX > screenWidth - radius) {
                    newX = screenWidth.toFloat() - radius
                    state.reset(-1)
                }

                bubble.circle.x = newX
            } else {
                bubble.position.startTime = SystemClock.elapsedRealtime()
            }
            updated.add(bubble)
        }
        return updated
    }
}