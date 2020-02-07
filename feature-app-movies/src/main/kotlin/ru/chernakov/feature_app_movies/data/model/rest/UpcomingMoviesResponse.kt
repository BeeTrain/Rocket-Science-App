package ru.chernakov.feature_app_movies.data.model.rest

import com.google.gson.annotations.SerializedName
import ru.chernakov.feature_app_movies.data.model.Movie

data class UpcomingMoviesResponse(
    @SerializedName("results")
    val movies: List<Movie>
)