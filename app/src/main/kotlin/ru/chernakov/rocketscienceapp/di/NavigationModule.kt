package ru.chernakov.rocketscienceapp.di

import org.koin.dsl.module
import ru.chernakov.feature_app_bubblegame.navigation.BubbleGameNavigation
import ru.chernakov.feature_appfeatures.navigation.AppFeaturesNavigation
import ru.chernakov.feature_favorite.navigaton.FavoriteNavigation
import ru.chernakov.feature_login.presentation.navigation.LoginNavigation
import ru.chernakov.feature_profile.navigation.ProfileNavigation
import ru.chernakov.feature_register.navigation.RegisterNavigation
import ru.chernakov.feature_settings.navigation.SettingsNavigation
import ru.chernakov.feature_splash.navigation.SplashNavigation
import ru.chernakov.rocketscienceapp.navigation.MainNavigator

val navigationModule = module {
    single { MainNavigator() }
    single<SplashNavigation> { get<MainNavigator>() }
    single<LoginNavigation> { get<MainNavigator>() }
    single<RegisterNavigation> { get<MainNavigator>() }
    single<FavoriteNavigation> { get<MainNavigator>() }
    single<ProfileNavigation> { get<MainNavigator>() }
    single<SettingsNavigation> { get<MainNavigator>() }
    single<AppFeaturesNavigation> { get<MainNavigator>() }
    single<BubbleGameNavigation> { get<MainNavigator>() }
}