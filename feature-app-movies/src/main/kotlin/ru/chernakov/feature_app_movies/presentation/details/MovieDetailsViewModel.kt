package ru.chernakov.feature_app_movies.presentation.details

import androidx.lifecycle.MutableLiveData
import ru.chernakov.feature_app_movies.data.model.Movie
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.util.data.GsonSerialization

class MovieDetailsViewModel(movieJson: String) : BaseViewModel() {
    val movieData = MutableLiveData<Movie>()

    init {
        movieData.value = GsonSerialization.gson.fromJson(movieJson, Movie::class.java)
    }
}