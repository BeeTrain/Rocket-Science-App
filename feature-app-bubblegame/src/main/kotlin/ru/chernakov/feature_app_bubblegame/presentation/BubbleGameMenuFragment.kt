package ru.chernakov.feature_app_bubblegame.presentation

import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.feature_app_bubblegame.R

class BubbleGameMenuFragment : BaseFragment() {
    private val menuViewModel: BubbleGameMenuViewModel by viewModel()

    override fun getLayout() = R.layout.fragment_bubblegame_menu

    override fun obtainViewModel() = menuViewModel

    companion object {
        fun newInstance() = BubbleGameMenuFragment()
    }
}