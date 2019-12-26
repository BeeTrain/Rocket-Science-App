package ru.chernakov.feature_profile.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.chernakov.feature_profile.presentation.ProfileViewModel

val profileModule = module {
    viewModel { ProfileViewModel(get()) }
}