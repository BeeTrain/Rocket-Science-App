package ru.chernakov.rocketscienceapp.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.chernakov.rocketscienceapp.presentation.ui.chat.ChatViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.feed.FeedViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.login.LoginViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.splash.SplashViewModel

val viewModelModule = module {
    viewModel { SplashViewModel() }
    viewModel { LoginViewModel() }
    viewModel { FeedViewModel() }
    viewModel { ChatViewModel(get()) }
}