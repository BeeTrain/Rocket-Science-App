package ru.chernakov.feature_appfeatures.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.chernakov.feature_appfeatures.data.domain.AppFeaturesInteractor
import ru.chernakov.feature_appfeatures.data.repository.AppFeaturesRepository
import ru.chernakov.feature_appfeatures.presentation.AppFeaturesViewModel

val appFeaturesModule = module {
    factory { AppFeaturesRepository() }
    factory { AppFeaturesInteractor(get()) }
    viewModel { AppFeaturesViewModel(get()) }
}