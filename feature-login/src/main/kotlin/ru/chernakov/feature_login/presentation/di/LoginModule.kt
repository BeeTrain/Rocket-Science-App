package ru.chernakov.feature_login.presentation.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.chernakov.feature_login.presentation.presentation.LoginViewModel

val loginModule = module {
    viewModel { LoginViewModel(get(), get()) }
}