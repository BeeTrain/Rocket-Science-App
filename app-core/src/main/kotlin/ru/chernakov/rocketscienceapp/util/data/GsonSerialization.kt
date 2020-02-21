package ru.chernakov.rocketscienceapp.util.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonSerialization {
    @JvmStatic
    val requestGson: Gson by lazy {
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS'Z'")
            .create()
    }

    @JvmStatic
    val gson: Gson by lazy {
        Gson()
    }
}