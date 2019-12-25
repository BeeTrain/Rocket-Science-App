package ru.chernakov.core_base.extension.java.lang

import ru.chernakov.core_base.extension.java.util.DD_MM_YYYY_HH_MM
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

inline fun String.toDateLong(pattern: String = DD_MM_YYYY_HH_MM): Long {
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    var date = Date()
    try {
        date = simpleDateFormat.parse(this)
    } catch (dateException: ParseException) {
        Timber.e(dateException)
    }
    return date.time
}
