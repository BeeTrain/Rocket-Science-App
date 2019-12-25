package ru.chernakov.rocketscienceapp.presentation.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.core_ui.extension.android.content.restartActivity
import ru.chernakov.core_ui.extension.androidx.fragment.app.replaceFragment
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel

class SettingsFragment : BaseFragment() {
    private val settingsViewModel: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivClose.setOnClickListener { fragmentManager?.popBackStack() }
        btLogout.setOnClickListener { settingsViewModel.logoutUser() }
        settingsViewModel.logoutEvent.observe(this, Observer {
            context?.restartActivity()
        })
    }

    override fun getLayout(): Int = R.layout.fragment_settings

    override fun obtainViewModel(): BaseViewModel = settingsViewModel

    companion object {
        fun newInstance(fragmentManager: FragmentManager) {
            fragmentManager.replaceFragment(SettingsFragment()).apply {
                addToBackStack = true
                enter = R.anim.slide_up
                exit = R.anim.fade_out
                popEnter = R.anim.fade_in
                popExit = R.anim.slide_down
                commit()
            }
        }
    }
}