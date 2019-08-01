package ru.chernakov.rocketscienceapp.presentation.ui.profile

import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.presentation.ui.base.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.presentation.ui.base.viewmodel.BaseViewModel

class ProfileFragment : BaseFragment() {
    override fun getLayout(): Int = R.layout.fragment_profile

    override fun obtainViewModel(): BaseViewModel = BaseViewModel()

    companion object {

        fun newInstance() = ProfileFragment()
    }
}