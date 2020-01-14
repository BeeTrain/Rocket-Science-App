package ru.chernakov.feature_app_bubblegame.domain

import android.graphics.Color
import android.os.SystemClock
import android.view.MotionEvent
import ru.chernakov.feature_app_bubblegame.data.GameSpeed
import ru.chernakov.feature_app_bubblegame.data.GameStatus
import ru.chernakov.feature_app_bubblegame.data.GameTime
import ru.chernakov.feature_app_bubblegame.data.model.Circle
import ru.chernakov.feature_app_bubblegame.presentation.widget.BubbleGameStatusListener
import ru.chernakov.feature_app_bubblegame.util.BubblePositionUtil
import ru.chernakov.feature_app_bubblegame.util.TouchEventProcessor
import java.util.Random
import kotlin.math.roundToInt

class BubbleGameInteractor(
    private var bubblePositionUtil: BubblePositionUtil,
    private var touchProcessor: TouchEventProcessor
) {
    var screenWidth: Int = 0
    var screenHeight: Int = 0
    var bubbleCount = MIN_BUBBLES_COUNT
    var passedTimeMs: Long = 0

    private var startTimeMs: Long = 0

    lateinit var gameTime: GameTime
    lateinit var speed: GameSpeed

    var circles: List<Circle> = listOf()
        private set

    var status = GameStatus.STOPPED
        private set

    var statusCallback: BubbleGameStatusListener? = null
        set(value) {
            field = value
            field?.onGameStatusChanged(status)
        }

    fun start() {
        placeCircles()

        startTimeMs = SystemClock.elapsedRealtime()
        passedTimeMs = 0
        touchProcessor.setGame(this)
        bubblePositionUtil.setGame(this)

        updateStatus(GameStatus.RUNNING)
    }

    fun update() {
        if (status != GameStatus.RUNNING) {
            return
        }

        passedTimeMs = SystemClock.elapsedRealtime() - startTimeMs
        bubblePositionUtil.update()

        if (touchProcessor.isAllCirclesHavePointers()) {
            end(true)
        } else if (passedTimeMs > gameTime.timeMs) {
            end(false)
        }
    }

    private fun end(isVictory: Boolean) {
        updateStatus(if (isVictory) GameStatus.WIN else GameStatus.LOSS)
    }

    private fun createCircle(radius: Int, vPos: Int, hPos: Int): Circle {
        val cx: Float = hPos * (radius * 2f) + radius
        val cy: Float = vPos * (radius * 2f) + radius

        val rand = Random()
        val color = Color.rgb(rand.nextInt(RAND_BOUND), rand.nextInt(RAND_BOUND), rand.nextInt(RAND_BOUND))

        return Circle(radius.toFloat(), cx, cy, color)
    }

    fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (status != GameStatus.RUNNING || event == null) {
            false
        } else {
            touchProcessor.processTouchEvent(event)
        }
    }

    private fun placeCircles() {
        val circles: MutableList<Circle> = ArrayList(bubbleCount)
        val characteristic: ScreenParams = calculateScreenParams()

        val verticalInd: MutableSet<Int> = HashSet(characteristic.verticalCellCount)
        val rand = Random()
        for (i in 0 until bubbleCount) {
            var vPos = rand.nextInt(characteristic.verticalCellCount)
            var hPos: Int
            while (true) {
                if (!verticalInd.contains(vPos)) {
                    verticalInd.add(vPos)
                    hPos = rand.nextInt(characteristic.horizontalCellCount)

                    break
                }

                vPos++
            }
            circles.add(i, createCircle(characteristic.circleRadius, vPos, hPos))
        }

        this.circles = ArrayList(circles)
    }

    private fun calculateScreenParams(): ScreenParams {
        val cellSize: Float = screenHeight / MAX_BUBBLES_COUNT.toFloat()
        val verticalCount = (screenHeight / cellSize - VALUE_HALF).roundToInt()
        val horizontalCount = (screenWidth / cellSize - VALUE_HALF).roundToInt()
        val radius = (cellSize / 2 - VALUE_HALF).roundToInt()

        return ScreenParams(radius, verticalCount, horizontalCount)
    }

    fun updateStatus(newStatus: GameStatus) {
        if (status != newStatus) {
            status = newStatus
            statusCallback?.onGameStatusChanged(newStatus)
        }
    }

    class ScreenParams(val circleRadius: Int, val verticalCellCount: Int, val horizontalCellCount: Int)

    companion object {
        private const val MIN_BUBBLES_COUNT = 3
        private const val MAX_BUBBLES_COUNT = 10
        private const val RAND_BOUND = 256
        private const val VALUE_HALF = .5f
    }
}