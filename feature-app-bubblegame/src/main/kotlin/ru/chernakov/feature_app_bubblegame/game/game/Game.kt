package ru.chernakov.feature_app_bubblegame.game.game

import android.graphics.Color
import android.os.SystemClock
import android.view.MotionEvent
import ru.chernakov.feature_app_bubblegame.data.GameSpeed
import ru.chernakov.feature_app_bubblegame.data.GameSpeed.STATIC
import ru.chernakov.feature_app_bubblegame.data.GameStatus
import ru.chernakov.feature_app_bubblegame.data.GameStatus.LOSS
import ru.chernakov.feature_app_bubblegame.data.GameStatus.STOPPED
import ru.chernakov.feature_app_bubblegame.data.GameStatus.RUNNING
import ru.chernakov.feature_app_bubblegame.data.GameStatus.WIN
import ru.chernakov.feature_app_bubblegame.data.GameTime
import ru.chernakov.feature_app_bubblegame.data.GameTime.EASY
import ru.chernakov.feature_app_bubblegame.data.model.Circle
import ru.chernakov.feature_app_bubblegame.game.game.logic.CirclePositionCalculator
import ru.chernakov.feature_app_bubblegame.game.game.logic.TouchEventProcessor
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

private const val MAX_CIRCLES_COUNT = 10
private const val MIN_CIRCLES_COUNT = 3

class Game private constructor(params: Params) {
    companion object Starter {
        private var game: Game? = null
        private var params: Params? = null

        fun obtainParams(): Params {
            if (params == null) {
                params = Params()
            }

            return params!!
        }

        fun startGame(): Game {
            val params = obtainParams()

            if (params.screenWidth <= 0 || params.screenHeight <= 0) {
                throw IllegalArgumentException("width and height of canvas must be > 0!")
            }

            if (params.circleCount < MIN_CIRCLES_COUNT || params.circleCount > MAX_CIRCLES_COUNT) {
                throw IllegalArgumentException("circles must be in range from $MIN_CIRCLES_COUNT to $MAX_CIRCLES_COUNT")
            }

            game = Game(params)
            game!!.start()
            return game!!
        }

        fun getInstance(): Game? {
            return game
        }

        fun resetParams() {
            params = null
        }
    }

    var statusCallback: StatusCallback? = null
        set(value) {
            field = value
            field?.onGameStatusChanged(this, status)
        }
    val roundTime: GameTime
    val speed: GameSpeed
    var circles: List<Circle>
        private set
    private val circleCount: Int
    private lateinit var positionCalculator: CirclePositionCalculator
    private var touchProcessor: TouchEventProcessor? = null
    val screenWidth: Int
    val screenHeight: Int

    var status = STOPPED
        private set

    var passedTimeMs: Long = 0
        private set
    private var startTimeMs: Long = 0

    init {
        roundTime = params.gameTime
        speed = params.gameSpeed
        circleCount = params.circleCount
        screenHeight = params.screenHeight
        screenWidth = params.screenWidth
        circles = ArrayList()
    }

    private fun start() {
        placeCircles()
        startTimer()

        updateStatusAndNotify(RUNNING)
    }

    fun update() {
        if (status != RUNNING) {
            return
        }

        passedTimeMs = SystemClock.elapsedRealtime() - startTimeMs
        positionCalculator.update()

        if (touchProcessor!!.isAllCirclesHavePointers()) {
            endGame(true)
        } else if (passedTimeMs > roundTime.timeMs) {
            endGame(false)
        }
    }

    fun onTouchEvent(event: MotionEvent?): Boolean {
        if (status != RUNNING || event == null || touchProcessor == null) {
            return false
        } else {
            return touchProcessor?.processTouchEvent(event) ?: false
        }
    }

    private fun startTimer() {
        startTimeMs = SystemClock.elapsedRealtime()
        passedTimeMs = 0
        positionCalculator = CirclePositionCalculator(this)
        touchProcessor = TouchEventProcessor(this)
    }

    private fun endGame(result: Boolean) {
        val newStatus = if (result) WIN else LOSS
        updateStatusAndNotify(newStatus)
    }

    private fun updateStatusAndNotify(newStatus: GameStatus) {
        if (status != newStatus) {
            status = newStatus
            statusCallback?.onGameStatusChanged(this, newStatus)
        }
    }

    private fun placeCircles() {
        val circles: MutableList<Circle> = ArrayList(circleCount)
        val characteristic: Characteristic = calculateScreenCharacteristics()

        val verticalInd: MutableSet<Int> = HashSet(characteristic.verticalCellCount)
        val rand = Random()
        for (i in 0 until circleCount) {
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

    private fun createCircle(radius: Int, vertPos: Int, horPos: Int): Circle {
        val cx: Float = horPos * (radius * 2f) + radius
        val cy: Float = vertPos * (radius * 2f) + radius

        val rand = Random()
        val color = Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))

        return Circle(radius.toFloat(), cx, cy, color)
    }

    private fun calculateScreenCharacteristics(): Characteristic {
        val cellSize: Float = screenHeight / MAX_CIRCLES_COUNT.toFloat()
        val verticalCount = Math.round(screenHeight / cellSize - .5f)
        val horizontalCount = Math.round(screenWidth / cellSize - .5f)
        val radius = Math.round(cellSize / 2 - .5f)

        return Characteristic(radius, verticalCount, horizontalCount)

    }

    data class Params(
        var screenWidth: Int = 0,
        var screenHeight: Int = 0,
        var circleCount: Int = MIN_CIRCLES_COUNT,
        var gameSpeed: GameSpeed = STATIC,
        var gameTime: GameTime = EASY
    )

    private data class Characteristic(val circleRadius: Int, val verticalCellCount: Int, val horizontalCellCount: Int)

    interface StatusCallback {
        fun onGameStatusChanged(game: Game, gameStatus: GameStatus)
    }
}