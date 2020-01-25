package ru.chernakov.feature_app_bubblegame.presentation.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import ru.chernakov.feature_app_bubblegame.data.GameStatus
import ru.chernakov.feature_app_bubblegame.data.model.Circle
import ru.chernakov.feature_app_bubblegame.domain.BubbleGameInteractor

class BubbleGameView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    private val circlePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var isScreenSizeSet = false
    private var callback: BubbleGameStateListener? = null
    var game: BubbleGameInteractor? = null

    fun setParamsCallback(listener: BubbleGameStateListener) {
        callback = listener
        isScreenSizeSet = false

        invalidate()
    }

    @SuppressLint("ClickableViewAccessibility")
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
        val circles = game?.bubbles?.map { it.circle }
        for (item: Circle in circles.orEmpty()) {
            val radius = item.radius
            circlePaint.color = item.color
            canvas?.drawCircle(item.x, item.y, radius, circlePaint)
        }

        invalidate()
    }

    private fun setScreenParams(width: Int, height: Int) {
        game?.screenWidth = width
        game?.screenHeight = height
        callback?.onScreenParamsSet()

        isScreenSizeSet = true
    }
}