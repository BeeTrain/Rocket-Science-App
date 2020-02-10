package ru.chernakov.feature_app_movies.presentation.movies

import androidx.lifecycle.MutableLiveData
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel
import ru.chernakov.feature_app_movies.data.model.Movie
import ru.chernakov.feature_app_movies.domain.LoadMoviesInteractor

class MoviesViewModel(private val loadMoviesInteractor: LoadMoviesInteractor) : BaseViewModel() {
    val moviesData = MutableLiveData<Pair<List<Movie>, Int>>()

    private var loadedPage = 1

    init {
        loadMore()
    }

    fun loadMore() {
        launchLoadingErrorJob {
            moviesData.postValue(
                Pair(loadMoviesInteractor.loadMovies(loadedPage), loadedPage).also {
                    loadedPage++
                }
            )
        }
    }
}