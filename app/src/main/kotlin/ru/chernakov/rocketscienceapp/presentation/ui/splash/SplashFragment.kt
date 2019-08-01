package ru.chernakov.rocketscienceapp.presentation.ui.splash

import android.os.Bundle
import android.view.View
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.presentation.ui.base.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.presentation.ui.base.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.flow.FlowFragment
import ru.chernakov.rocketscienceapp.presentation.ui.login.LoginFragment


class SplashFragment : BaseFragment() {
    private val splashViewModel: SplashViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        redirectToNextScreen(splashViewModel.getUser() != null)
    }

    override fun getLayout(): Int = R.layout.fragment_splash

    override fun obtainViewModel(): BaseViewModel = splashViewModel

    private fun redirectToNextScreen(isLogged: Boolean) {
        if (isLogged) {
            startFragment(FlowFragment.newInstance(), false)
        } else {
            startFragment(LoginFragment.newInstance(), false)
        }
    }
}