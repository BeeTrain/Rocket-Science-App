package ru.chernakov.feature_app_bubblegame.presentation.running

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import kotlinx.android.synthetic.main.fragment_bubble_game_running.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.feature_app_bubblegame.R
import ru.chernakov.feature_app_bubblegame.navigation.OnBackPressedListener
import ru.chernakov.feature_app_bubblegame.presentation.host.BubbleGameHostFragment
import ru.chernakov.feature_app_bubblegame.presentation.host.BubbleGameViewModel
import ru.chernakov.feature_app_bubblegame.presentation.widget.BubbleGameStateListener

class BubbleGameRunningFragment : BaseFragment() {
    private val runningViewModel: BubbleGameViewModel by viewModel()

    private lateinit var onBackPressedListener: OnBackPressedListener
    private lateinit var gameStateListener: BubbleGameStateListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bubbleGameView.gameInteractor = runningViewModel.gameInteractor
        bubbleGameView.setParamsCallback(gameStateListener)

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressedListener.onRunningBackPressed()
            }
        })
    }

    override fun getLayout() = R.layout.fragment_bubble_game_running

    override fun obtainViewModel() = runningViewModel

    companion object {
        fun newInstance(hostFragment: BubbleGameHostFragment): BubbleGameRunningFragment {
            return BubbleGameRunningFragment().apply {
                gameStateListener = hostFragment
                onBackPressedListener = hostFragment
            }
        }
    }
}