package ru.chernakov.feature_app_movies.data.model

import com.google.gson.annotations.SerializedName
import ru.chernakov.feature_app_movies.BuildConfig

data class Movie(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("overview")
    val overview: String
) {

    fun getPosterLoadingUrl() = posterBaseUrl + posterPath

    companion object {
        private const val posterBaseUrl = BuildConfig.API_IMAGE_URL
    }
}