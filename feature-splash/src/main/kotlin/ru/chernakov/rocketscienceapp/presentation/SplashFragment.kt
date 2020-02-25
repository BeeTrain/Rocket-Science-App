package ru.chernakov.rocketscienceapp.presentation

import android.os.Bundle
import android.view.View
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.navigation.SplashNavigation
import ru.chernakov.rocketscienceapp.splash.R
import ru.chernakov.rocketscienceapp.presentation.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel

class SplashFragment : BaseFragment() {
    private val splashViewModel: SplashViewModel by viewModel()
    private val navigation: SplashNavigation by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        redirectToNextScreen(splashViewModel.getUser() != null)
    }

    override fun getLayout(): Int = R.layout.fragment_splash

    override fun obtainViewModel(): BaseViewModel = splashViewModel

    private fun redirectToNextScreen(isLogged: Boolean) {
        if (isLogged) {
            navigation.fromSplashToAppFeatures()
        } else {
            navigation.fromSplashToLogin()
        }
    }
}