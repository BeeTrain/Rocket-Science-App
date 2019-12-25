package ru.chernakov.core_ui.extension.android.widget

import android.graphics.PorterDuff
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat

fun EditText.changeEditTextBottomLineColor(@ColorInt color: Int) {
    return background.mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
}

fun EditText.changeEditTextCursorColor(@ColorInt color: Int) {
    // Get the cursor resource id
    var field = TextView::class.java.getDeclaredField("mCursorDrawableRes")
    field.isAccessible = true
    val drawableResId = field.getInt(this)

    // Get the editor
    field = TextView::class.java.getDeclaredField("mEditor")
    field.isAccessible = true
    val editor = field.get(this)

    // Get the drawable and set a color filter
    val drawable = ContextCompat.getDrawable(context, drawableResId)
    drawable!!.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    val drawables = arrayOf(drawable, drawable)

    // Set the drawables
    field = editor.javaClass.getDeclaredField("mCursorDrawable")
    field.isAccessible = true
    field.set(editor, drawables)
}