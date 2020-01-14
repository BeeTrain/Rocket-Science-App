@file:Suppress("NOTHING_TO_INLINE", "unused")

package ru.chernakov.core_base.extension.java.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

const val DD_MM_YYYY = "dd.MM.yyyy"
const val HH_MM = "HH:mm"
const val DD_MM_YYYY_HH_MM = "dd.MM.yyyy HH:mm"
const val SERVER_ISO8601_DATE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

inline fun todayDate() = Date(Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE_UTC)).timeInMillis)

inline fun Date.convert(year: Int, month: Int, day: Int, pattern: String = DD_MM_YYYY): String {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day)
    val date = calendar.time
    return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
}