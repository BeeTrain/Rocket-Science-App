package ru.chernakov.rocketscienceapp.navigation

import androidx.appcompat.app.AppCompatActivity
import ru.chernakov.feature_app_bubblegame.navigation.BubbleGameNavigation
import ru.chernakov.feature_app_bubblegame.presentation.BubbleGameMenuFragment
import ru.chernakov.feature_appfeatures.navigation.AppFeaturesNavigation
import ru.chernakov.feature_appfeatures.presentation.AppFeaturesFragment
import ru.chernakov.feature_flow.navigation.FlowNavigation
import ru.chernakov.feature_flow.presentation.FlowFragment
import ru.chernakov.feature_login.presentation.navigation.LoginNavigation
import ru.chernakov.feature_login.presentation.presentation.LoginFragment
import ru.chernakov.feature_profile.navigation.ProfileNavigation
import ru.chernakov.feature_profile.presentation.ProfileFragment
import ru.chernakov.feature_register.navigation.RegisterNavigation
import ru.chernakov.feature_register.presentation.RegisterFragment
import ru.chernakov.feature_settings.navigation.SettingsNavigation
import ru.chernakov.feature_splash.navigation.SplashNavigation
import ru.chernakov.feature_splash.presentation.SplashFragment
import ru.chernakov.rocketscienceapp.R

class Navigator : SplashNavigation, LoginNavigation, RegisterNavigation, FlowNavigation, ProfileNavigation,
    SettingsNavigation, AppFeaturesNavigation, BubbleGameNavigation {

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

    override fun openProfile() {
        navController?.subNavigate(ProfileFragment.newInstance(), false)
    }

    override fun openList() {
        navController?.subNavigate(AppFeaturesFragment.newInstance(), false)
    }

    override fun fromProfileToSettings() {
        navController?.navigate(
            ru.chernakov.feature_settings.presentation.SettingsFragment.newInstance(),
            animation = NavigationAnimation(
                R.anim.slide_up,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_down
            )
        )
    }

    override fun logoutFromSettings() {
        navController?.restartHost()
    }

    override fun openBubbleGame() {
        navController?.navigate(BubbleGameMenuFragment.newInstance())
    }
}