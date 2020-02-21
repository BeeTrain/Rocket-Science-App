package ru.chernakov.rocketscienceapp.domain

import ru.chernakov.rocketscienceapp.data.repository.MoviesRepository

class LoadMoviesInteractor(private val moviesRepository: MoviesRepository) {

    suspend fun loadMovies(page: Int) = moviesRepository.loadMoviesPage(page)
}