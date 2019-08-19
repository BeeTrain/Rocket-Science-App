package ru.chernakov.rocketscienceapp

import android.app.Application
import leakcanary.LeakSentry
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.chernakov.rocketscienceapp.di.appModule
import ru.chernakov.rocketscienceapp.di.interactorModule
import ru.chernakov.rocketscienceapp.di.viewModelModule
import ru.chernakov.rocketscienceapp.util.lifecycle.Lifecycler
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
            modules(listOf(viewModelModule, appModule, interactorModule))
        }
    }

    private fun initLeakDetection() {
        if (BuildConfig.DEBUG) {
            LeakSentry.config = LeakSentry.config.copy(watchFragmentViews = false)
        }
    }
}