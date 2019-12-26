package ru.chernakov.rocketscienceapp.di

import org.koin.dsl.module
import ru.chernakov.feature_login.presentation.navigation.LoginNavigation
import ru.chernakov.feature_register.navigation.RegisterNavigation
import ru.chernakov.feature_splash.navigation.SplashNavigation
import ru.chernakov.rocketscienceapp.navigation.Navigator

val navigationModule = module {
    single { Navigator() }
    single<SplashNavigation> { get<Navigator>() }
    single<LoginNavigation> { get<Navigator>() }
    single<RegisterNavigation> { get<Navigator>() }
}