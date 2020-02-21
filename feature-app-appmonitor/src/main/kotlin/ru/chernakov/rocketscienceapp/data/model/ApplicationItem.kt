package ru.chernakov.rocketscienceapp.data.model

import android.graphics.drawable.Drawable

data class ApplicationItem(
    var name: String,
    var appPackage: String,
    var version: String,
    var source: String,
    var data: String,
    var isSystem: Boolean,
    var sha: String,
    var installDate: Long,
    var updateDate: Long,
    var fromPlayMarket: Boolean,
    var appSize: Long,
    var icon: Drawable
)