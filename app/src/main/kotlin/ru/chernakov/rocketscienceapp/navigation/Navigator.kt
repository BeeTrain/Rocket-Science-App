package ru.chernakov.rocketscienceapp.navigation

import androidx.appcompat.app.AppCompatActivity
import ru.chernakov.feature_login.presentation.navigation.LoginNavigation
import ru.chernakov.feature_login.presentation.presentation.LoginFragment
import ru.chernakov.feature_register.navigation.RegisterNavigation
import ru.chernakov.feature_splash.navigation.SplashNavigation
import ru.chernakov.feature_splash.presentation.SplashFragment
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.presentation.ui.flow.FlowFragment
import ru.chernakov.feature_register.presentation.RegisterFragment

class Navigator : SplashNavigation, LoginNavigation, RegisterNavigation {

    private var navController: NavigationController? = null

    fun bind(activity: AppCompatActivity) {
        navController = NavigationController(activity)
    }

    fun unbind() {
        navController = null
    }

    override fun startSplash() {
        navController?.navigate(SplashFragment())
    }

    override fun fromSplashToFlow() {
        navController?.navigate(FlowFragment.newInstance(), false)
    }

    override fun fromSplashToLogin() {
        navController?.navigate(LoginFragment.newInstance(), false)
    }

    override fun fromLoginToRegister() {
        navController?.navigate(
            RegisterFragment.newInstance(),
            animation = NavigationAnimation(
                R.anim.slide_up,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_down
            )
        )
    }

    override fun fromLoginToFlow() {
        navController?.navigate(FlowFragment.newInstance(), false)
    }

    override fun fromRegisterToFlow() {
        navController?.navigate(FlowFragment.newInstance(), false)
    }
}