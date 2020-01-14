package ru.chernakov.feature_settings.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.chernakov.feature_settings.presentation.SettingsViewModel

val settingsModule = module {
    viewModel { SettingsViewModel(get()) }
}