@file:Suppress("NOTHING_TO_INLINE")

package ru.chernakov.core_base.extension.java.lang

import ru.chernakov.core_base.extension.java.util.DD_MM_YYYY_HH_MM
import java.text.SimpleDateFormat
import java.util.*

inline fun Long.formatToDateString(pattern: String = DD_MM_YYYY_HH_MM): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(this))
}

inline fun Long.toDate() = Date(this)