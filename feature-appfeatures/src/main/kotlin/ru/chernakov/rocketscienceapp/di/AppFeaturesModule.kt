package ru.chernakov.rocketscienceapp.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.chernakov.rocketscienceapp.data.domain.AppFeaturesInteractor
import ru.chernakov.rocketscienceapp.data.repository.AppFeaturesRepository
import ru.chernakov.rocketscienceapp.presentation.AppFeaturesViewModel

val appFeaturesModule = module {
    single { AppFeaturesRepository() }
    factory { AppFeaturesInteractor(get()) }
    viewModel { AppFeaturesViewModel(get()) }
}