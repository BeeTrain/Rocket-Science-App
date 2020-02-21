package ru.chernakov.rocketscienceapp.data.repository

import ru.chernakov.rocketscienceapp.data.model.Movie

class MoviesRepository(private val theMovieDbRepository: TheMovieDbRepository) {

    suspend fun loadMoviesPage(page: Int): List<Movie> {
        return theMovieDbRepository.getUpcomingMovies(page).movies
    }
}