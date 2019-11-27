package ru.chernakov.rocketscienceapp.extension.java.util

import java.util.Calendar
import java.util.Date
import java.util.TimeZone

const val TIMEZONE_UTC = "UTC"

inline fun createUtcCalendar() = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE_UTC))

inline fun createUtcCalendar(date: Date) = createUtcCalendar().apply { time = date }

inline fun createUtcCalendar(millis: Long) = createUtcCalendar().apply { timeInMillis = millis }