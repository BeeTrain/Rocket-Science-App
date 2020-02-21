package ru.chernakov.rocketscienceapp.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.navigation.SettingsNavigation
import ru.chernakov.rocketscienceapp.presentation.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel

class SettingsFragment : BaseFragment() {
    private val settingsViewModel: SettingsViewModel by viewModel()
    private val navigator: SettingsNavigation by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivClose.setOnClickListener { navigator.fromSettingsToProfile() }
        btLogout.setOnClickListener { settingsViewModel.logoutUser() }
        settingsViewModel.logoutEvent.observe(viewLifecycleOwner, Observer {
            navigator.logoutFromSettings()
        })
    }

    override fun getLayout(): Int = R.layout.fragment_settings

    override fun obtainViewModel(): BaseViewModel = settingsViewModel
}