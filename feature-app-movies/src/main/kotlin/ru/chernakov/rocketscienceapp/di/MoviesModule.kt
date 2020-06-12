package ru.chernakov.rocketscienceapp.di

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.chernakov.rocketscienceapp.movies.BuildConfig
import ru.chernakov.rocketscienceapp.data.repository.MoviesRepository
import ru.chernakov.rocketscienceapp.data.repository.TheMovieDbRepository
import ru.chernakov.rocketscienceapp.data.repository.UrlRepository
import ru.chernakov.rocketscienceapp.domain.LoadMoviesInteractor
import ru.chernakov.rocketscienceapp.movies.R
import ru.chernakov.rocketscienceapp.presentation.details.MovieDetailsViewModel
import ru.chernakov.rocketscienceapp.presentation.movies.MoviesViewModel
import ru.chernakov.rocketscienceapp.util.data.GsonSerialization
import java.util.concurrent.TimeUnit

private const val TIMEOUT = 60L

val moviesModule = module {

    single {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        builder.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val originalUrl = original.url

                val newUrl = originalUrl.newBuilder()
                    .addQueryParameter(
                        TheMovieDbRepository.API_KEY,
                        BuildConfig.THE_MOVIEDB_API_KEY
                    )
                    .build()

                val request = original.newBuilder().url(newUrl)

                return chain.proceed(request.build())
            }
        })

        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS)
        builder.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl((get() as UrlRepository).baseUrl)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(GsonSerialization.requestGson))
            .build()
    }

    single {
        (get() as Retrofit).newBuilder()
            .baseUrl((get() as UrlRepository).baseUrl)
            .build()
            .create(TheMovieDbRepository::class.java)
    }

    single { UrlRepository() }

    single { MoviesRepository(get()) }

    factory { LoadMoviesInteractor(get()) }

    viewModel { MoviesViewModel(get()) }

    viewModel { (movieJson: String) -> MovieDetailsViewModel(movieJson) }
}