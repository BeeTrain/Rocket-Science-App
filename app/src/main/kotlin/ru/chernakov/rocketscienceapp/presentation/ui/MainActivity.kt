package ru.chernakov.rocketscienceapp.presentation.ui

import androidx.fragment.app.Fragment
import ru.chernakov.core_ui.presentation.activity.BaseActivity
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.presentation.ui.splash.SplashFragment

class MainActivity : BaseActivity() {

    override fun createFragment(): Fragment = SplashFragment()

    override fun getLayout(): Int = R.layout.activity_base
}