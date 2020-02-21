package ru.chernakov.rocketscienceapp

import android.app.Application
import leakcanary.LeakSentry
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.chernakov.rocketscienceapp.util.lifecycle.Lifecycler
import ru.chernakov.feature_app_appmonitor.di.appMonitorModule
import ru.chernakov.feature_app_bubblegame.di.bubbleGameModule
import ru.chernakov.feature_app_movies.di.moviesModule
import ru.chernakov.feature_appfeatures.di.appFeaturesModule
import ru.chernakov.feature_favorite.di.favoriteModule
import ru.chernakov.feature_login.presentation.di.loginModule
import ru.chernakov.feature_profile.di.profileModule
import ru.chernakov.feature_register.di.registerModule
import ru.chernakov.feature_settings.di.settingsModule
import ru.chernakov.feature_splash.di.splashModule
import ru.chernakov.rocketscienceapp.di.appModule
import ru.chernakov.rocketscienceapp.di.firebaseModule
import ru.chernakov.rocketscienceapp.di.navigationModule
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        initKoin()
        initLeakDetection()
        Lifecycler.register(this)
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    firebaseModule,
                    navigationModule,
                    splashModule,
                    loginModule,
                    registerModule,
                    favoriteModule,
                    profileModule,
                    settingsModule,
                    appFeaturesModule,
                    bubbleGameModule,
                    moviesModule,
                    appMonitorModule
                )
            )
        }
    }

    private fun initLeakDetection() {
        if (BuildConfig.DEBUG) {
            LeakSentry.config = LeakSentry.config.copy(watchFragmentViews = false)
        }
    }
}