package ru.chernakov.rocketscienceapp.data.model

import com.google.gson.annotations.SerializedName
import ru.chernakov.rocketscienceapp.BuildConfig

data class Movie(
    @SerializedName("id") var id: Long,
    @SerializedName("title") var title: String,
    @SerializedName("poster_path") var posterPath: String,
    @SerializedName("overview") var overview: String,
    @SerializedName("release_date") var releaseDate: String,
    @SerializedName("vote_average") var voteAverage: String
) {

    fun getPosterLoadingUrl() = posterBaseUrl + posterPath

    companion object {
        private const val posterBaseUrl = BuildConfig.API_IMAGE_URL
    }
}