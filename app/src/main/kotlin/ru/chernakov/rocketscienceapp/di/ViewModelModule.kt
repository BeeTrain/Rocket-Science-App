package ru.chernakov.rocketscienceapp.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.chernakov.rocketscienceapp.presentation.ui.flow.FlowViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.profile.ProfileViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.settings.SettingsViewModel

val viewModelModule = module {
    viewModel { FlowViewModel() }
    viewModel { ProfileViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}