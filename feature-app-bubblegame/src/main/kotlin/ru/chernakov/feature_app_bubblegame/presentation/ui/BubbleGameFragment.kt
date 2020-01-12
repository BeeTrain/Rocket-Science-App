package ru.chernakov.feature_app_bubblegame.presentation.ui

import android.os.Bundle
import android.view.View
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.feature_app_bubblegame.R

class BubbleGameFragment : BaseFragment() {
    private val bubbleGameViewModel: BubbleGameViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun getLayout() = R.layout.fragment_bubble_game

    override fun obtainViewModel() = bubbleGameViewModel

    companion object {
        fun newInstance() = BubbleGameFragment()
    }
}