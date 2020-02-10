package ru.chernakov.feature_app_movies.data.repository

import ru.chernakov.feature_app_movies.data.model.Movie

class MoviesRepository(private val theMovieDbRepository: TheMovieDbRepository) {

    suspend fun loadMoviesPage(page: Int): List<Movie> {
        return theMovieDbRepository.getUpcomingMovies(page).movies
    }
}