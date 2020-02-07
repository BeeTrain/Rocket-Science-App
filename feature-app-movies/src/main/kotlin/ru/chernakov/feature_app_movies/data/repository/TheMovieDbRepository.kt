package ru.chernakov.feature_app_movies.data.repository

import retrofit2.http.GET
import ru.chernakov.feature_app_movies.data.model.rest.UpcomingMoviesResponse

interface TheMovieDbRepository {

    @GET(UPCOMING)
    suspend fun getUpcomingMovies(): UpcomingMoviesResponse

    companion object {
        const val API_KEY = "api_key"
        const val LANGUAGE = "language"
        const val PAGE = "page"

        private const val UPCOMING = "movie/upcoming"
    }
}