package ru.chernakov.feature_app_bubblegame.presentation.running

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_bubble_game_running.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.core_base.util.lifecycle.SafeObserver
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.feature_app_bubblegame.R
import ru.chernakov.feature_app_bubblegame.data.GameStatus
import ru.chernakov.feature_app_bubblegame.navigation.BubbleGameNavigation
import ru.chernakov.feature_app_bubblegame.presentation.BubbleGameViewModel

class BubbleGameRunningFragment : BaseFragment() {
    private val bubbleGameViewModel: BubbleGameViewModel by viewModel()
    private val navigator: BubbleGameNavigation by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bubbleGameView.game = bubbleGameViewModel.gameInteractor

        bubbleGameViewModel.gameInteractor.status.observe(viewLifecycleOwner, SafeObserver {
            when (it) {
                GameStatus.LOSS, GameStatus.WIN -> {
                    navigator.openBubbleGameResult()
                }
                else -> {
                }
            }
        })
    }

    override fun getLayout() = R.layout.fragment_bubble_game_running

    override fun obtainViewModel() = bubbleGameViewModel
}