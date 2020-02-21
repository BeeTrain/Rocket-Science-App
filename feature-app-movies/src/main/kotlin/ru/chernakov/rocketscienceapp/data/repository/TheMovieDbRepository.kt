package ru.chernakov.rocketscienceapp.data.repository

import retrofit2.http.GET
import retrofit2.http.Query
import ru.chernakov.rocketscienceapp.data.model.rest.UpcomingMoviesResponse

interface TheMovieDbRepository {

    @GET(UPCOMING)
    suspend fun getUpcomingMovies(@Query("page") page: Int): UpcomingMoviesResponse

    companion object {
        const val API_KEY = "api_key"
        const val LANGUAGE = "language"
        const val PAGE = "page"

        private const val UPCOMING = "movie/upcoming"
    }
}