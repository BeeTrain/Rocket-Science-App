package ru.chernakov.rocketscienceapp.di

import org.koin.dsl.module
import ru.chernakov.rocketscienceapp.navigation.AppMonitorNavigation
import ru.chernakov.rocketscienceapp.navigation.BubbleGameNavigation
import ru.chernakov.rocketscienceapp.navigation.MoviesNavigation
import ru.chernakov.rocketscienceapp.navigation.AppFeaturesNavigation
import ru.chernakov.rocketscienceapp.navigaton.FavoriteNavigation
import ru.chernakov.rocketscienceapp.presentation.navigation.LoginNavigation
import ru.chernakov.rocketscienceapp.navigation.ProfileNavigation
import ru.chernakov.rocketscienceapp.navigation.RegisterNavigation
import ru.chernakov.rocketscienceapp.navigation.SettingsNavigation
import ru.chernakov.feature_splash.navigation.SplashNavigation
import ru.chernakov.rocketscienceapp.navigation.BottomNavigation
import ru.chernakov.rocketscienceapp.navigation.MainNavigator

val navigationModule = module {
    single { MainNavigator() }
    single<SplashNavigation> { get<MainNavigator>() }
    single<LoginNavigation> { get<MainNavigator>() }
    single<RegisterNavigation> { get<MainNavigator>() }
    single<BottomNavigation> { get<MainNavigator>() }
    single<FavoriteNavigation> { get<MainNavigator>() }
    single<ProfileNavigation> { get<MainNavigator>() }
    single<SettingsNavigation> { get<MainNavigator>() }
    single<AppFeaturesNavigation> { get<MainNavigator>() }
    single<BubbleGameNavigation> { get<MainNavigator>() }
    single<MoviesNavigation> { get<MainNavigator>() }
    single<AppMonitorNavigation> { get<MainNavigator>() }
}