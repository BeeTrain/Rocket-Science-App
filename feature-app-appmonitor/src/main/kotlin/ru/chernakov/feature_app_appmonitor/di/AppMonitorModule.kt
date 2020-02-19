package ru.chernakov.feature_app_appmonitor.di

import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.chernakov.feature_app_appmonitor.data.repository.ApplicationsRepository
import ru.chernakov.feature_app_appmonitor.domain.FetchAppsInteractor
import ru.chernakov.feature_app_appmonitor.domain.LoadAppInfoInteractor
import ru.chernakov.feature_app_appmonitor.domain.PrepareOptionsInteractor
import ru.chernakov.feature_app_appmonitor.presentation.info.AppInfoViewModel
import ru.chernakov.feature_app_appmonitor.presentation.list.AppsListViewModel

val appMonitorModule = module {

    factory { ApplicationsRepository(androidContext().packageManager) }

    factory { FetchAppsInteractor(get()) }
    factory { LoadAppInfoInteractor(get()) }
    factory { PrepareOptionsInteractor(get()) }

    viewModel { AppsListViewModel(get()) }
    viewModel { (appPackage: String) -> AppInfoViewModel(appPackage, get(), get()) }
}