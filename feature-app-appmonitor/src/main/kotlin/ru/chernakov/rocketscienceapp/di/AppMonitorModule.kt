package ru.chernakov.rocketscienceapp.di

import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.chernakov.rocketscienceapp.data.repository.ApplicationsRepository
import ru.chernakov.rocketscienceapp.domain.FetchAppsInteractor
import ru.chernakov.rocketscienceapp.domain.LoadAppInfoInteractor
import ru.chernakov.rocketscienceapp.domain.PrepareOptionsInteractor
import ru.chernakov.rocketscienceapp.presentation.info.AppInfoViewModel
import ru.chernakov.rocketscienceapp.presentation.list.AppsListViewModel

val appMonitorModule = module {

    factory { ApplicationsRepository(androidContext().packageManager) }

    factory { FetchAppsInteractor(get()) }
    factory { LoadAppInfoInteractor(get()) }
    factory { PrepareOptionsInteractor(get()) }

    viewModel { AppsListViewModel(get()) }
    viewModel { (appPackage: String) -> AppInfoViewModel(appPackage, get(), get()) }
}