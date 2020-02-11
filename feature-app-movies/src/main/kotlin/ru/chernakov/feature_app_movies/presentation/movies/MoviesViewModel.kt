package ru.chernakov.feature_app_movies.presentation.movies

import androidx.lifecycle.MutableLiveData
import ru.chernakov.core_base.util.lifecycle.SingleLiveEvent
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel
import ru.chernakov.feature_app_movies.data.model.Movie
import ru.chernakov.feature_app_movies.domain.LoadMoviesInteractor

class MoviesViewModel(private val loadMoviesInteractor: LoadMoviesInteractor) : BaseViewModel() {
    val moviesData = MutableLiveData<Set<Movie>>()
    val selectedMovieEvent = SingleLiveEvent<Movie>()

    private var loadedPage = 1

    init {
        loadMore()
    }

    private fun addMovies(newMovies: List<Movie>) {
        val updated = mutableSetOf<Movie>().apply {
            moviesData.value?.let { addAll(it) }
            addAll(newMovies)
        }
        moviesData.value = updated
    }

    fun loadMore() {
        launchLoadingErrorJob {
            addMovies(loadMoviesInteractor.loadMovies(loadedPage).also {
                loadedPage++
            })
        }
    }

    fun selectMovie(movie: Movie) {
        selectedMovieEvent.value = movie
    }
}