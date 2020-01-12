package ru.chernakov.feature_app_bubblegame.game.ui.screen

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import ru.chernakov.feature_app_bubblegame.data.GameStatus
import ru.chernakov.feature_app_bubblegame.data.model.Circle
import ru.chernakov.feature_app_bubblegame.game.game.Game
import ru.chernakov.feature_app_bubblegame.game.ui.GameParamsCallback

/**
 * @author mikhail.funikov@e-legion.com on 22/04/2017.
 */

class GameScreenView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val circlePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var isScreenSizeSet = false
    var game: Game? = null
    private var callback: GameParamsCallback? = null

    fun calculateParamsAndDrawGame(callback: GameParamsCallback) {
        this.callback = callback
        isScreenSizeSet = false

        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return game?.onTouchEvent(event) ?: false
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (w == oldw && h == oldh) return

        setScreenParams(w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        if (game == null || game?.status != GameStatus.RUNNING) {
            return
        }

        if (!isScreenSizeSet) {
            setScreenParams(width, height)
        }

        game?.update()
        val circles = game?.circles
        for (item: Circle in circles.orEmpty()) {
            val radius = item.radius
            circlePaint.color = item.color
            canvas?.drawCircle(item.x, item.y, radius, circlePaint)
        }

        invalidate()
    }

    private fun setScreenParams(w: Int, h: Int) {
        val params = Game.obtainParams()
        params.screenWidth = w
        params.screenHeight = h
        callback?.onScreenParamsSet(params)
        game = Game.getInstance()

        isScreenSizeSet = true
    }
}

