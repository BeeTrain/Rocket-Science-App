package ru.chernakov.core_ui.data.model

import com.google.gson.annotations.SerializedName

class ErrorBean(
    @SerializedName("code")
    var code: String?,
    @SerializedName("message")
    var message: String?
)