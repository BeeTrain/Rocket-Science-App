package ru.chernakov.rocketscienceapp.delegate.android.view

import android.view.View
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun View.isVisible(keepBounds: Boolean): ReadWriteProperty<Any, Boolean> {
    return object : ReadWriteProperty<Any, Boolean> {
        override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
            return visibility == View.VISIBLE
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
            visibility = when {
                value -> View.VISIBLE
                keepBounds -> View.INVISIBLE
                else -> View.GONE
            }
        }
    }
}