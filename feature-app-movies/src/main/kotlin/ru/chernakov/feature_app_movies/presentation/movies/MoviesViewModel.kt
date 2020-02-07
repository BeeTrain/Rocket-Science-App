package ru.chernakov.feature_app_movies.presentation.movies

import androidx.lifecycle.MutableLiveData
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel
import ru.chernakov.feature_app_movies.data.model.Movie
import ru.chernakov.feature_app_movies.domain.LoadMoviesInteractor

class MoviesViewModel(private val loadMoviesInteractor: LoadMoviesInteractor) : BaseViewModel() {
    val moviesData = MutableLiveData<List<Movie>>()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        launchLoadingErrorJob {
            moviesData.postValue(loadMoviesInteractor.loadMovies())
        }
    }
}