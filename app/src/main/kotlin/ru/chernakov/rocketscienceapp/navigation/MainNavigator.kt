package ru.chernakov.rocketscienceapp.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import ru.chernakov.core_ui.extension.androidx.fragment.app.replaceFragment
import ru.chernakov.feature_app_bubblegame.navigation.BubbleGameNavigation
import ru.chernakov.feature_app_bubblegame.presentation.host.BubbleGameHostFragment
import ru.chernakov.feature_app_bubblegame.presentation.menu.BubbleGameMenuFragment
import ru.chernakov.feature_app_bubblegame.presentation.result.BubbleGameResultFragment
import ru.chernakov.feature_app_bubblegame.presentation.running.BubbleGameRunningFragment
import ru.chernakov.feature_app_movies.navigation.MoviesNavigation
import ru.chernakov.feature_app_movies.presentation.movies.MoviesFragmentDirections
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
import ru.chernakov.rocketscienceapp.NavGraphDirections
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.presentation.MainActivity

@Suppress("TooManyMethods")
class MainNavigator : SplashNavigation, LoginNavigation, RegisterNavigation, BottomNavigation,
    ProfileNavigation, FavoriteNavigation, SettingsNavigation, AppFeaturesNavigation,
    BubbleGameNavigation, MoviesNavigation {

    private var activity: MainActivity? = null
    var navigation: NavController? = null

    fun bind(activity: MainActivity) {
        this.navigation = activity.findNavController(R.id.nav_host_container)
        this.activity = activity
    }

    fun unbind() {
        navigation = null
        activity = null
    }

    private fun navigate(action: NavDirections, showBottomNavigation: Boolean = false) {
        navigation?.navigate(action)
        activity?.setBottomNavigationVisibility(showBottomNavigation)
    }

    private fun subNavigate(
        fragment: Fragment,
        isAddToBackStack: Boolean = true,
        animation: NavigationAnimation? = null
    ) {
        activity?.supportFragmentManager?.replaceFragment(fragment, R.id.subContainer)?.apply {
            addToBackStack = isAddToBackStack
            animation?.let {
                enter = it.enter
                exit = it.exit
                popEnter = it.popEnter
                popExit = it.popExit
            }
            commit()
        }
    }

    override fun openAppFeatures() {
        navigate(NavGraphDirections.actionOpenAppFeatures(), true)
    }

    override fun openFavorite() {
        navigate(NavGraphDirections.actionOpenFavorite(), true)
    }

    override fun openProfile() {
        navigate(NavGraphDirections.actionOpenProfile(), true)
    }

    override fun fromSplashToLogin() {
        navigate(SplashFragmentDirections.actionFromSplashToLogin())
    }

    override fun fromSplashToAppFeatures() {
        openProfile()
    }

    override fun fromLoginToRegister() {
        navigate(LoginFragmentDirections.actionFromLoginToRegister())
    }

    override fun fromLoginToAppFeatures() {
        openAppFeatures()
    }

    override fun fromRegisterToAppFeatures() {
        navigate(RegisterFragmentDirections.actionFromRegisterToAppFeatures(), true)
    }

    override fun fromRegisterToLogin() {
        navigation?.popBackStack()
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

    override fun fromSettingsToProfile() {
        navigate(SettingsFragmentDirections.actionFromSettingsToProfile(), true)
    }

    override fun fromAppFeaturesToFavorite() {
        navigate(AppFeaturesFragmentDirections.actionFromAppFeaturesToFavorite(), true)
    }

    override fun fromAppFeaturesToProfile() {
        navigate(AppFeaturesFragmentDirections.actionFromAppFeaturesToProfile(), true)
    }

    override fun fromFavoriteToAppFeatures() {
        navigate(FavoriteFragmentDirections.actionFromFavoriteToAppFeatures(), true)
    }

    override fun fromFavoriteToProfile() {
        navigate(FavoriteFragmentDirections.actionFromFavoriteToProfile(), true)
    }

    override fun fromBubbleGameToAppFeatures() {
        openAppFeatures()
    }

    override fun openBubbleGame() {
        navigate(AppFeaturesFragmentDirections.actionOpenBubbleGame())
    }

    override fun openBubbleGameMenu(hostFragment: BubbleGameHostFragment) {
        subNavigate(BubbleGameMenuFragment.newInstance(hostFragment), false)
    }

    override fun startBubbleGame(hostFragment: BubbleGameHostFragment) {
        subNavigate(BubbleGameRunningFragment.newInstance(hostFragment), false)
    }

    override fun openBubbleGameResult(hostFragment: BubbleGameHostFragment) {
        subNavigate(BubbleGameResultFragment.newInstance(hostFragment), false)
    }

    override fun openMovies() {
        navigate(AppFeaturesFragmentDirections.actionOpenMovies())
    }

    override fun fromMoviesToDetails() {
        navigate(MoviesFragmentDirections.actionFromMoviesToDetails())
    }
}