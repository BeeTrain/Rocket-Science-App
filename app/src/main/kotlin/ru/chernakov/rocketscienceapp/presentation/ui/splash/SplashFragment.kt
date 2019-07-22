package ru.chernakov.rocketscienceapp.presentation.ui.splash

import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.presentation.ui.base.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.presentation.ui.base.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.feed.FeedFragment
import ru.chernakov.rocketscienceapp.presentation.ui.login.LoginFragment


class SplashFragment : BaseFragment() {
    private val viewModel: SplashViewModel by viewModel()

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        redirectToNextScreen(firebaseAuth.currentUser != null)
    }

    override fun getLayout(): Int = R.layout.fragment_splash

    override fun obtainViewModel(): BaseViewModel = viewModel

    private fun redirectToNextScreen(isLogged: Boolean) {
        if (isLogged) {
            startFragment(FeedFragment.newInstance())
        } else {
            startFragment(LoginFragment.newInstance())
        }
    }
}