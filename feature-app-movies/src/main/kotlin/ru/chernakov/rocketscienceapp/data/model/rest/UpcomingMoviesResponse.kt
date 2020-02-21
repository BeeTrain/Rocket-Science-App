package ru.chernakov.rocketscienceapp.data.model.rest

import com.google.gson.annotations.SerializedName
import ru.chernakov.rocketscienceapp.data.model.Movie

data class UpcomingMoviesResponse(
    @SerializedName("results")
    val movies: List<Movie>
)