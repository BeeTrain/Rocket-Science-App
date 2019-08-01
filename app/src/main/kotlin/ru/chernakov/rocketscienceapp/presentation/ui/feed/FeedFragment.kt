package ru.chernakov.rocketscienceapp.presentation.ui.feed

import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.presentation.ui.base.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.presentation.ui.base.viewmodel.BaseViewModel

class FeedFragment : BaseFragment() {
    override fun getLayout(): Int = R.layout.fragment_feed

    override fun obtainViewModel(): BaseViewModel = BaseViewModel()

    companion object {

        fun newInstance() = FeedFragment()
    }
}