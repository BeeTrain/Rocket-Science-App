@file:Suppress("NOTHING_TO_INLINE")

package ru.chernakov.rocketscienceapp.extension.java.lang

import ru.chernakov.rocketscienceapp.extension.util.DD_MM_YYYY_HH_MM
import java.text.SimpleDateFormat
import java.util.*

inline fun Long.formatToDateString(pattern: String = DD_MM_YYYY_HH_MM): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(this))
}

inline fun Long.toDate() = Date(this)