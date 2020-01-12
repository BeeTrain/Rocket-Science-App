package ru.chernakov.feature_app_bubblegame.presentation.widget

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class BubbleGameView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    private val circlePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var isScreenSizeSet = false


}