package ru.chernakov.rocketscienceapp.di

import org.koin.dsl.module
import ru.chernakov.feature_app_bubblegame.navigation.BubbleGameNavigation
import ru.chernakov.feature_appfeatures.navigation.AppFeaturesNavigation
import ru.chernakov.feature_flow.navigation.FlowNavigation
import ru.chernakov.feature_login.presentation.navigation.LoginNavigation
import ru.chernakov.feature_profile.navigation.ProfileNavigation
import ru.chernakov.feature_register.navigation.RegisterNavigation
import ru.chernakov.feature_settings.navigation.SettingsNavigation
import ru.chernakov.feature_splash.navigation.SplashNavigation
import ru.chernakov.rocketscienceapp.navigation.Navigator

val navigationModule = module {
    single { Navigator() }
    single<SplashNavigation> { get<Navigator>() }
    single<LoginNavigation> { get<Navigator>() }
    single<RegisterNavigation> { get<Navigator>() }
    single<FlowNavigation> { get<Navigator>() }
    single<ProfileNavigation> { get<Navigator>() }
    single<SettingsNavigation> { get<Navigator>() }
    single<AppFeaturesNavigation> { get<Navigator>() }
    single<BubbleGameNavigation> { get<Navigator>() }
}