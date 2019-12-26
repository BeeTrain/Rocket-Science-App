package ru.chernakov.feature_settings.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel
import ru.chernakov.feature_settings.R
import ru.chernakov.feature_settings.navigation.SettingsNavigation

class SettingsFragment : BaseFragment() {
    private val settingsViewModel: SettingsViewModel by viewModel()
    private val navigator: SettingsNavigation by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivClose.setOnClickListener { fragmentManager?.popBackStack() }
        btLogout.setOnClickListener { settingsViewModel.logoutUser() }
        settingsViewModel.logoutEvent.observe(viewLifecycleOwner, Observer {
            navigator.logoutFromSettings()
        })
    }

    override fun getLayout(): Int = R.layout.fragment_settings

    override fun obtainViewModel(): BaseViewModel = settingsViewModel

    companion object {
        fun newInstance() = SettingsFragment()
    }
}