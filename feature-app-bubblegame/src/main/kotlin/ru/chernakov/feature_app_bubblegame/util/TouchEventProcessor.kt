package ru.chernakov.feature_app_bubblegame.util

import android.view.MotionEvent
import ru.chernakov.feature_app_bubblegame.data.model.Bubble
import ru.chernakov.feature_app_bubblegame.data.model.Circle
import ru.chernakov.feature_app_bubblegame.domain.BubbleGameInteractor
import kotlin.math.pow
import kotlin.math.sqrt

class TouchEventProcessor {
    private var bubbles: List<Bubble> = listOf()

    fun setGame(bubbleGameInteractor: BubbleGameInteractor) {
        bubbles = bubbleGameInteractor.bubbles
    }

    fun processTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_MOVE -> handleActionMove(event)
            else -> handleOtherActions(event)
        }

        return true
    }

    fun isAllCirclesHavePointers(): Boolean {
        return !bubbles.any { item -> !item.hasPointers() }
    }

    private fun handleActionMove(event: MotionEvent) {
        val historySize = event.historySize
        val pointerCount = event.pointerCount

        for (i in 0 until historySize) {
            for (k in 0 until pointerCount) {
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
        for (state in bubbles) {
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
        for (state in bubbles) {
            state.pointerIds.remove(pointerId)
        }
    }

    private fun freeCirclesFromPointers() {
        for (state in bubbles) {
            state.pointerIds.clear()
        }
    }

    private fun calculateTouchRadius(c: Circle, x: Float, y: Float): Float {
        return sqrt((x - c.x).toDouble().pow(2.toDouble()) + (y - c.y).toDouble().pow(2.toDouble())).toFloat()
    }
}