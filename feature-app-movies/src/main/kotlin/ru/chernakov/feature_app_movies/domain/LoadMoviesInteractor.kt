package ru.chernakov.feature_app_movies.domain

import ru.chernakov.feature_app_movies.data.repository.MoviesRepository

class LoadMoviesInteractor(private val moviesRepository: MoviesRepository) {

    suspend fun loadMovies(page: Int) = moviesRepository.loadMoviesPage(page)
}