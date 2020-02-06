package ru.chernakov.feature_app_movies.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.chernakov.feature_app_movies.presentation.info.MovieInfoViewModel
import ru.chernakov.feature_app_movies.presentation.movies.MoviesViewModel

val moviesModule = module {

    viewModel { MoviesViewModel() }

    viewModel { MovieInfoViewModel() }
}