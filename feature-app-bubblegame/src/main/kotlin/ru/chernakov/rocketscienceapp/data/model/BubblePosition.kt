package ru.chernakov.rocketscienceapp.data.model

import android.os.SystemClock

class BubblePosition(var startTime: Long, var stateTime: Long, var startX: Float, var direction: Int) {
    fun reset(direction: Int) {
        this.direction = direction
        startTime = SystemClock.elapsedRealtime()
        stateTime = SystemClock.elapsedRealtime()
        startX = 0f
    }
}