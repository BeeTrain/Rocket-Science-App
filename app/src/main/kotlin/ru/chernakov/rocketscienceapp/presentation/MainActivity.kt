package ru.chernakov.rocketscienceapp.presentation

import android.content.Context
import android.content.Intent
import org.koin.android.ext.android.inject
import ru.chernakov.core_ui.presentation.activity.BaseActivity
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.navigation.Navigator

class MainActivity : BaseActivity() {
    private val navigator: Navigator by inject()

    private var isFirstLaunch: Boolean = true

    override fun onPause() {
        super.onPause()
        navigator.unbind()
    }

    override fun onResume() {
        super.onResume()
        navigator.bind(this)
        if (isFirstLaunch) navigator.startSplash()
        isFirstLaunch = false
    }

    override fun getLayout(): Int = R.layout.activity_base

    companion object {
        fun makeIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}