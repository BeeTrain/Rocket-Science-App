package ru.chernakov.rocketscienceapp.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.chernakov.rocketscienceapp.presentation.profile.ProfileViewModel
import ru.chernakov.rocketscienceapp.presentation.settings.SettingsViewModel

val profileModule = module {
    viewModel { ProfileViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}