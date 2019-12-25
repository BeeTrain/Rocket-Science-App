package ru.chernakov.core_base.extension.java.lang

import ru.chernakov.core_base.extension.java.util.DD_MM_YYYY_HH_MM
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

inline fun Long.formatToDateString(pattern: String = DD_MM_YYYY_HH_MM): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(this))
}