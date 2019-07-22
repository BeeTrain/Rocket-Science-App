package ru.chernakov.rocketscienceapp.extension.android.text

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

class CustomTypefaceSpan(private val typeface: Typeface) : MetricAffectingSpan() {

    override fun updateDrawState(ds: TextPaint) {
        apply(ds, typeface)
    }

    override fun updateMeasureState(paint: TextPaint) {
        apply(paint, typeface)
    }

    private fun apply(paint: Paint, tf: Typeface) {
        val oldStyle: Int

        val old = paint.typeface
        oldStyle = old?.style ?: 0

        val fake = oldStyle and tf.style.inv()

        if (fake and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }

        if (fake and Typeface.ITALIC != 0) {
            paint.textSkewX = STANDARD_SKEW_X
        }

        paint.typeface = tf
    }

    companion object {
        const val STANDARD_SKEW_X = -0.25f
    }
}