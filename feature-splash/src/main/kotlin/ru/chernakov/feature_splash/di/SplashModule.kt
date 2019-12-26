package ru.chernakov.feature_splash.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.chernakov.feature_splash.presentation.SplashViewModel

val splashModule = module {
    viewModel { SplashViewModel(get()) }
}