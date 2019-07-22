package ru.chernakov.rocketscienceapp.presentation.ui.main

import androidx.fragment.app.Fragment
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.presentation.ui.base.activity.BaseActivity
import ru.chernakov.rocketscienceapp.presentation.ui.splash.SplashFragment

class MainActivity : BaseActivity() {

    override fun createFragment(): Fragment = SplashFragment()

    override fun getLayout(): Int = R.layout.activity_base
}