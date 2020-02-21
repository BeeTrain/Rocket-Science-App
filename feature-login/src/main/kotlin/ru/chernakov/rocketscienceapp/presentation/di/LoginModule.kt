package ru.chernakov.rocketscienceapp.presentation.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.chernakov.rocketscienceapp.presentation.presentation.LoginViewModel

val loginModule = module {
    viewModel { LoginViewModel(get(), get()) }
}