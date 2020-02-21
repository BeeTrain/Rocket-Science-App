package ru.chernakov.rocketscienceapp.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.chernakov.rocketscienceapp.presentation.SettingsViewModel

val settingsModule = module {
    viewModel { SettingsViewModel(get()) }
}