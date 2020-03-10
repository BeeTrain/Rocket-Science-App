package ru.chernakov.rocketscienceapp.presentation.movies

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.delay
import ru.chernakov.rocketscienceapp.data.model.Movie
import ru.chernakov.rocketscienceapp.domain.LoadMoviesInteractor
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel

class MoviesViewModel(private val loadMoviesInteractor: LoadMoviesInteractor) : BaseViewModel() {
    val moviesData = MutableLiveData<Set<Movie>>()
    val networkErrorEvent = MutableLiveData<Boolean>()

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
        if (networkErrorEvent.value == true) networkErrorEvent.value = false
        loadedPage++
    }

    fun loadMore() {
        launchLoadingErrorJob(exceptionBlock = { handleLoadingException() }) {
            delay(BEFORE_LOADING_DELAY)
            addMovies(loadMoviesInteractor.loadMovies(loadedPage))
        }
    }

    private fun handleLoadingException() {
        networkErrorEvent.value = true
    }

    companion object {
        private const val BEFORE_LOADING_DELAY = 500L
    }
}