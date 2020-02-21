package ru.chernakov.rocketscienceapp.util

import android.os.SystemClock
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import ru.chernakov.rocketscienceapp.data.GameSpeed
import ru.chernakov.rocketscienceapp.data.model.Bubble

class BubblePositionUtil {
    private val interpolator: Interpolator = LinearInterpolator()

    fun update(bubbles: List<Bubble>, gameSpeed: GameSpeed, screenWidth: Int): List<Bubble> {
        val updated = mutableListOf<Bubble>()
        for (bubble in bubbles) {
            val radius = bubble.circle.radius
            val state = bubble.position
            val coef = state.stateTime / gameSpeed.timeMills.toFloat()
            val interpolated = interpolator.getInterpolation(coef)

            val posDelta = screenWidth * interpolated
            val bubblePos = if (state.direction == -1) screenWidth - radius else radius
            var newX = state.startX + posDelta * state.direction + bubblePos

            if (!bubble.hasPointers()) {
                if (newX < radius) {
                    newX = radius
                    state.reset(1)
                } else if (newX > screenWidth - radius) {
                    newX = screenWidth.toFloat() - radius
                    state.reset(-1)
                }

                bubble.circle.x = newX
                bubble.position.stateTime = SystemClock.elapsedRealtime() - state.startTime
            } else {
                bubble.position.startTime = state.stateTime - state.startTime
            }
            bubble.position.stateTime = SystemClock.elapsedRealtime() - state.startTime
            updated.add(bubble)
        }
        return updated
    }
}