package ru.chernakov.feature_app_bubblegame.data.model

import android.os.SystemClock

class BubblePosition(var startTime: Long, var startX: Float, var direction: Int) {
    fun reset(direction: Int) {
        this.direction = direction
        startTime = SystemClock.elapsedRealtime()
        startX = 0f
    }
}