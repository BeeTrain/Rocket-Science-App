package ru.chernakov.feature_splash.presentation

import android.os.Bundle
import android.view.View
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel
import ru.chernakov.feature_splash.R
import ru.chernakov.feature_splash.navigation.SplashNavigation

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
            navigation.fromSplashToFlow()
        } else {
            navigation.fromSplashToLogin()
        }
    }
}