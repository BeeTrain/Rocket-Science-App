package ru.chernakov.feature_app_bubblegame.game.game.logic

import android.view.MotionEvent
import ru.chernakov.feature_app_bubblegame.data.model.Circle
import ru.chernakov.feature_app_bubblegame.game.game.Game

internal class TouchEventProcessor constructor(game: Game) {
    private val touchStates: List<State>

    init {
        touchStates = List(game.circles.size) { index -> State(game.circles[index]) }
    }

    fun processTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_MOVE -> handleActionMove(event)
            else -> handleOtherActions(event)
        }

        return true
    }

    fun isAllCirclesHavePointers(): Boolean {
        return !touchStates.any { item -> !item.hasPointers() }
    }

    private fun handleActionMove(event: MotionEvent) {
        val historySize = event.historySize
        val pointerCount = event.pointerCount

        for (i in 0..historySize - 1) {
            for (k in 0..pointerCount - 1) {
                checkPointer(event.getPointerId(k), event.getHistoricalX(k, i), event.getHistoricalY(k, i))
            }
        }
    }

    private fun handleOtherActions(event: MotionEvent) {
        val action = event.action
        var pointerIndex = -1
        var isDownAction = false

        when (action) {
            MotionEvent.ACTION_DOWN -> {

                pointerIndex = 0
                isDownAction = true
            }
            MotionEvent.ACTION_UP -> {
                pointerIndex = 0
                isDownAction = false
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                pointerIndex = event.actionIndex
                isDownAction = true
            }
            MotionEvent.ACTION_POINTER_UP -> {
                pointerIndex = event.actionIndex
                isDownAction = false
            }
            MotionEvent.ACTION_CANCEL -> freeCirclesFromPointers()
        }

        if (pointerIndex != -1) {
            val pointerId = event.getPointerId(pointerIndex)
            if (isDownAction) {
                checkPointer(pointerId, event.getX(pointerIndex), event.getY(pointerIndex))
            } else {
                removePointer(pointerId)
            }
        }
    }

    private fun checkPointer(pointerId: Int, x: Float, y: Float) {
        for (state in touchStates) {
            val circle = state.circle
            val maxY = circle.y + circle.radius
            val minY = circle.y - circle.radius
            val maxX = circle.x + circle.radius
            val minX = circle.x - circle.radius

            if (y in minY..maxY &&
                x in minX..maxX &&
                circle.radius >= calculateTouchRadius(circle, x, y)
            ) {
                state.pointerIds.add(pointerId)
            } else {
                state.pointerIds.remove(pointerId)
            }
        }
    }

    private fun removePointer(pointerId: Int) {
        for (state in touchStates) {
            state.pointerIds.remove(pointerId)
        }
    }

    private fun freeCirclesFromPointers() {
        for (state in touchStates) {
            state.pointerIds.clear()
        }
    }

    private fun calculateTouchRadius(circle: Circle, x: Float, y: Float): Float {
        return Math.sqrt(
            Math.pow((x - circle.x).toDouble(), 2.toDouble()) + Math.pow(
                (y - circle.y).toDouble(),
                2.toDouble()
            )
        ).toFloat()
    }

    private data class State(var circle: Circle, val pointerIds: MutableSet<Int> = mutableSetOf()) {
        fun hasPointers(): Boolean {
            return !pointerIds.isEmpty()
        }
    }
}