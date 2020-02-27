package ru.chernakov.rocketscienceapp.presentation

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.navigation.SettingsNavigation
import ru.chernakov.rocketscienceapp.presentation.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.settings.R

class SettingsFragment : BaseFragment() {
    private val settingsViewModel: SettingsViewModel by viewModel()
    private val navigator: SettingsNavigation by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btLogout.setOnClickListener { settingsViewModel.logoutUser() }
        settingsViewModel.logoutEvent.observe(viewLifecycleOwner, Observer {
            navigator.logoutFromSettings()
        })

        initCloseButton()
    }

    private fun initCloseButton() {
        ivClose.isClickable = false
        ivClose.setOnClickListener { navigator.fromSettingsToProfile() }
        Handler().postDelayed({ ivClose.isClickable = true }, INIT_CLOSE_DELAY)
    }

    override fun getLayout(): Int = R.layout.fragment_settings

    override fun obtainViewModel(): BaseViewModel = settingsViewModel

    companion object {
        private const val INIT_CLOSE_DELAY = 500L
    }
}