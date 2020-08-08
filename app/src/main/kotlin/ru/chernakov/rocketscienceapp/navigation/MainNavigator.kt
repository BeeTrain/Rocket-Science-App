package ru.chernakov.rocketscienceapp.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import ru.chernakov.rocketscienceapp.NavGraphDirections
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.extension.androidx.fragment.app.replaceFragment
import ru.chernakov.rocketscienceapp.navigaton.FavoriteNavigation
import ru.chernakov.rocketscienceapp.presentation.AppFeaturesFragmentDirections
import ru.chernakov.rocketscienceapp.presentation.FavoriteFragmentDirections
import ru.chernakov.rocketscienceapp.presentation.MainActivity
import ru.chernakov.rocketscienceapp.presentation.SplashFragmentDirections
import ru.chernakov.rocketscienceapp.presentation.details.MovieDetailsFragment
import ru.chernakov.rocketscienceapp.presentation.host.BubbleGameHostFragment
import ru.chernakov.rocketscienceapp.presentation.info.AppInfoFragment
import ru.chernakov.rocketscienceapp.presentation.list.AppsListFragmentDirections
import ru.chernakov.rocketscienceapp.presentation.login.LoginFragmentDirections
import ru.chernakov.rocketscienceapp.presentation.menu.BubbleGameMenuFragment
import ru.chernakov.rocketscienceapp.presentation.movies.MoviesFragmentDirections
import ru.chernakov.rocketscienceapp.presentation.profile.ProfileFragmentDirections
import ru.chernakov.rocketscienceapp.presentation.register.RegisterFragmentDirections
import ru.chernakov.rocketscienceapp.presentation.result.BubbleGameResultFragment
import ru.chernakov.rocketscienceapp.presentation.running.BubbleGameRunningFragment
import ru.chernakov.rocketscienceapp.presentation.settings.SettingsFragmentDirections

@Suppress("TooManyFunctions")
class MainNavigator : SplashNavigation, AuthNavigation, BottomNavigation,
    ProfileNavigation, FavoriteNavigation, AppFeaturesNavigation,
    BubbleGameNavigation, MoviesNavigation, AppMonitorNavigation, PaintNavigation {

    private var activity: MainActivity? = null
    var navigation: NavController? = null

    private fun navigate(
        action: NavDirections,
        showBottomNavigation: Boolean = false,
        args: Bundle? = null,
        navExtras: FragmentNavigator.Extras? = null
    ) {
        navigation?.navigate(action.actionId, args, null, navExtras)
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

    fun bind(activity: MainActivity) {
        this.navigation = activity.findNavController(R.id.nav_host_container)
        this.activity = activity
    }

    fun unbind() {
        navigation = null
        activity = null
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

    override fun fromMoviesToAppFeatures() {
        openAppFeatures()
    }

    override fun fromMoviesToDetails(navigationExtras: FragmentNavigator.Extras, movieJson: String) {
        navigate(
            MoviesFragmentDirections.actionFromMoviesToDetails(),
            args = MovieDetailsFragment.createArgs(movieJson),
            navExtras = navigationExtras
        )
    }

    override fun openAppMonitor() {
        navigate(AppFeaturesFragmentDirections.actionOpenAppMonitor())
    }

    override fun fromAppMonitorToAppFeatures() {
        openAppFeatures()
    }

    override fun fromAppsListToInfo(packageId: String) {
        navigate(AppsListFragmentDirections.actionFromAppsListToAppInfo(), args = AppInfoFragment.createArgs(packageId))
    }

    override fun openPaint() {
        navigate(AppFeaturesFragmentDirections.actionOpenPaint())
    }

    override fun fromPaintToAppFeatures() {
        openAppFeatures()
    }
}