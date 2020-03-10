package ru.chernakov.rocketscienceapp.delegate.android.widget

import android.widget.ProgressBar
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun ProgressBar.progress(): ReadWriteProperty<Any, Float> {
    return object : ReadWriteProperty<Any, Float> {
        override fun getValue(thisRef: Any, property: KProperty<*>): Float {
            return if (max == 0) 0f else progress / max.toFloat()
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Float) {
            progress = (value * max).toInt()
        }
    }
}