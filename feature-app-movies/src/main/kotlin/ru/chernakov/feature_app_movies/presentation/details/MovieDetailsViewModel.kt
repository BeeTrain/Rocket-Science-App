package ru.chernakov.feature_app_movies.presentation.details

import androidx.lifecycle.MutableLiveData
import ru.chernakov.core_base.util.data.GsonSerialization
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel
import ru.chernakov.feature_app_movies.data.model.Movie

class MovieDetailsViewModel(movieJson: String) : BaseViewModel() {
    val movieData = MutableLiveData<Movie>()

    init {
        movieData.value = GsonSerialization.gson.fromJson(movieJson, Movie::class.java)
    }
}