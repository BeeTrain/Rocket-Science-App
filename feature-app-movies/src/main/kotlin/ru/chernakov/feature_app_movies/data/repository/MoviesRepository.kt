package ru.chernakov.feature_app_movies.data.repository

import ru.chernakov.feature_app_movies.data.model.Movie

class MoviesRepository(private val theMovieDbRepository: TheMovieDbRepository) {

    suspend fun loadMovies(): List<Movie> {
        return theMovieDbRepository.getUpcomingMovies().movies
    }
}