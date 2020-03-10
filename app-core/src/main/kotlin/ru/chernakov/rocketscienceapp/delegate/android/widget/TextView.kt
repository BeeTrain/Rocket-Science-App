package ru.chernakov.rocketscienceapp.delegate.android.widget

import android.widget.TextView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun TextView.text(): ReadWriteProperty<Any, String> {
    return object : ReadWriteProperty<Any, String> {
        override fun getValue(thisRef: Any, property: KProperty<*>): String = text.toString()

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
            text = value
        }
    }
}