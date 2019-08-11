package ru.chernakov.rocketscienceapp.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.chernakov.rocketscienceapp.presentation.ui.flow.FlowViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.login.LoginViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.profile.ProfileViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.register.RegisterViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.settings.SettingsViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.splash.SplashViewModel

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { FlowViewModel() }
    viewModel { ProfileViewModel() }
    viewModel { SettingsViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
}