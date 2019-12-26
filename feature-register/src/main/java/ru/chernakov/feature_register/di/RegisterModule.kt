package ru.chernakov.feature_register.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.chernakov.feature_register.presentation.RegisterViewModel

val registerModule = module {
    viewModel { RegisterViewModel(get()) }

}