package ru.chernakov.rocketscienceapp

import android.app.Application
import leakcanary.LeakSentry
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.chernakov.rocketscienceapp.util.lifecycle.Lifecycler
import ru.chernakov.rocketscienceapp.di.appMonitorModule
import ru.chernakov.rocketscienceapp.di.bubbleGameModule
import ru.chernakov.rocketscienceapp.di.moviesModule
import ru.chernakov.rocketscienceapp.di.appFeaturesModule
import ru.chernakov.rocketscienceapp.di.favoriteModule
import ru.chernakov.rocketscienceapp.presentation.di.loginModule
import ru.chernakov.rocketscienceapp.di.profileModule
import ru.chernakov.rocketscienceapp.di.registerModule
import ru.chernakov.rocketscienceapp.di.settingsModule
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