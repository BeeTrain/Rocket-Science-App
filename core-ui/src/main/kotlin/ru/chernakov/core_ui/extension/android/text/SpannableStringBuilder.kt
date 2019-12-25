@file:Suppress("NOTHING_TO_INLINE")

package ru.chernakov.core_ui.extension.android.text

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Patterns
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

const val ROUBLE_SIGN = "\u20BD"

fun SpannableStringBuilder.appendImage(context: Context, @DrawableRes res: Int) = apply {
    appendAndSetSpan(" ", getImageSpan(context, res))
}

fun SpannableStringBuilder.appendColoredText(text: String, context: Context, @ColorRes color: Int) = apply {
    appendAndSetSpan(text, getColoredSpan(context, color))
}

fun SpannableStringBuilder.appendBoldText(text: String) = apply {
    appendAndSetSpan(text, StyleSpan(Typeface.BOLD))
}

/** GENERAL METHODS  */

inline fun String.isEmail(): Boolean = !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun fromHtml(html: String): CharSequence {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(html)
    }
}

fun SpannableStringBuilder.appendAndSetSpan(text: String, span: Any) {
    val from = length
    append(text)
    val to = length
    setSpan(span, from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
}

inline fun getImageSpan(context: Context, @DrawableRes icon: Int): ImageSpan {
    return getImageSpan(ContextCompat.getDrawable(context, icon)!!)
}

inline fun getImageSpan(context: Context, bitmap: Bitmap): ImageSpan {
    return getImageSpan(BitmapDrawable(context.resources, bitmap))
}

inline fun getImageSpan(drawable: Drawable): ImageSpan {
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    return ImageSpan(drawable, ImageSpan.ALIGN_BASELINE)
}

inline fun getColoredSpan(context: Context, @ColorRes color: Int): ForegroundColorSpan {
    return ForegroundColorSpan(ContextCompat.getColor(context, color))
}

inline fun getSizeSpan(relativeSize: Float) = RelativeSizeSpan(relativeSize)

/** END OF GENERAL METHODS  */