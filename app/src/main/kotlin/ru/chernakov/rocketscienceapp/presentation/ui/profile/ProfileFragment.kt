package ru.chernakov.rocketscienceapp.presentation.ui.profile

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.toolbar_profile.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.presentation.ui.base.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.presentation.ui.base.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.profile.settings.SettingsFragment

class ProfileFragment : BaseFragment() {
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivSettings.setOnClickListener {
            activity?.let { SettingsFragment.newInstance(it.supportFragmentManager) }
        }
    }

    override fun getLayout(): Int = R.layout.fragment_profile

    override fun obtainViewModel(): BaseViewModel = profileViewModel

    companion object {

        fun newInstance() = ProfileFragment()
    }
}