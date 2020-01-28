package ru.chernakov.rocketscienceapp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import ru.chernakov.feature_app_bubblegame.navigation.BubbleGameNavigation
import ru.chernakov.feature_app_bubblegame.presentation.menu.BubbleGameMenuFragmentDirections
import ru.chernakov.feature_app_bubblegame.presentation.result.BubbleGameResultFragmentDirections
import ru.chernakov.feature_app_bubblegame.presentation.running.BubbleGameRunningFragmentDirections
import ru.chernakov.feature_appfeatures.navigation.AppFeaturesNavigation
import ru.chernakov.feature_appfeatures.presentation.AppFeaturesFragmentDirections
import ru.chernakov.feature_favorite.navigaton.FavoriteNavigation
import ru.chernakov.feature_favorite.presentation.FavoriteFragmentDirections
import ru.chernakov.feature_login.presentation.navigation.LoginNavigation
import ru.chernakov.feature_login.presentation.presentation.LoginFragmentDirections
import ru.chernakov.feature_profile.navigation.ProfileNavigation
import ru.chernakov.feature_profile.presentation.ProfileFragmentDirections
import ru.chernakov.feature_register.navigation.RegisterNavigation
import ru.chernakov.feature_register.presentation.RegisterFragmentDirections
import ru.chernakov.feature_settings.navigation.SettingsNavigation
import ru.chernakov.feature_settings.presentation.SettingsFragmentDirections
import ru.chernakov.feature_splash.navigation.SplashNavigation
import ru.chernakov.feature_splash.presentation.SplashFragmentDirections
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.presentation.MainActivity

class MainNavigator : SplashNavigation, LoginNavigation, RegisterNavigation,
    ProfileNavigation, FavoriteNavigation, SettingsNavigation, AppFeaturesNavigation,
    BubbleGameNavigation {

    private lateinit var activity: MainActivity
    var navigation: NavController? = null

    fun bind(activity: MainActivity) {
        this.navigation = activity.findNavController(R.id.nav_host_container)
        this.activity = activity
    }

    fun unbind() {
        navigation = null
    }

    fun navigate(action: NavDirections, showBottomNavigation: Boolean = false) {
        navigation?.navigate(action)
        activity.setBottomNavigationVisibility(showBottomNavigation)
    }

    override fun fromSplashToLogin() {
        navigate(SplashFragmentDirections.actionFromSplashToLogin())
    }

    override fun fromSplashToAppFeatures() {
        navigate(SplashFragmentDirections.actionOpenAppFeatures(), true)
    }

    override fun fromLoginToRegister() {
        navigate(LoginFragmentDirections.actionFromLoginToRegister())
    }

    override fun fromLoginToAppFeatures() {
        navigate(LoginFragmentDirections.actionFromLoginToAppFeatures(), true)
    }

    override fun fromRegisterToAppFeatures() {
        navigate(RegisterFragmentDirections.actionFromRegisterToAppFeatures(), true)
    }

    override fun fromProfileToFavorite() {
        navigate(ProfileFragmentDirections.actionFromProfileToFavorite(), true)
    }

    override fun fromProfileToAppFeatures() {
        navigate(ProfileFragmentDirections.actionFromProfileToAppFeatures(), true)
    }

    override fun fromProfileToSettings() {
        navigate(ProfileFragmentDirections.actionFromProfileToSettings())
    }

    override fun logoutFromSettings() {
        navigate(SettingsFragmentDirections.actionLogoutFromSettings())
    }

    override fun fromAppFeaturesToFavorite() {
        navigate(AppFeaturesFragmentDirections.actionFromAppFeaturesToFavorite(), true)
    }

    override fun fromAppFeaturesToProfile() {
        navigate(AppFeaturesFragmentDirections.actionFromAppFeaturesToProfile(), true)
    }

    override fun openBubbleGame() {
        navigate(AppFeaturesFragmentDirections.actionOpenBubbleGame())
    }

    override fun startBubbleGame() {
        navigate(BubbleGameMenuFragmentDirections.actionBubbleFromMenuToGame())
    }

    override fun stopBubbleGame() {
        navigate(BubbleGameRunningFragmentDirections.actionBubbleFromGameToMenu())
    }

    override fun openBubbleGameResult() {
        navigate(BubbleGameRunningFragmentDirections.actionBubbleFromGameToResult())
    }

    override fun openBubbleGameMenu() {
        navigate(BubbleGameResultFragmentDirections.actionBubbleFromResultToMenu())
    }

    override fun fromFavoriteToAppFeatures() {
        navigate(FavoriteFragmentDirections.actionFromFavoriteToAppFeatures(), true)
    }

    override fun fromFavoriteToProfile() {
        navigate(FavoriteFragmentDirections.actionFromFavoriteToProfile(), true)
    }
}