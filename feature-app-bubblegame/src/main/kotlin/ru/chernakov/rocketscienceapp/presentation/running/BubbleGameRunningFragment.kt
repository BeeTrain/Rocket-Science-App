package ru.chernakov.rocketscienceapp.presentation.running

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import kotlinx.android.synthetic.main.fragment_bubble_game_running.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.navigation.OnBackPressedListener
import ru.chernakov.rocketscienceapp.presentation.host.BubbleGameHostFragment
import ru.chernakov.rocketscienceapp.presentation.host.BubbleGameViewModel
import ru.chernakov.rocketscienceapp.presentation.widget.BubbleGameStateListener
import ru.chernakov.rocketscienceapp.presentation.fragment.BaseFragment

class BubbleGameRunningFragment : BaseFragment() {
    private val bubbleGameViewModel: BubbleGameViewModel by viewModel()

    private lateinit var onBackPressedListener: OnBackPressedListener
    private lateinit var gameStateListener: BubbleGameStateListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bubbleGameView.game = bubbleGameViewModel.gameInteractor
        bubbleGameView.setParamsCallback(gameStateListener)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressedListener.onRunningBackPressed()
                }
            })
    }

    override fun getLayout() = R.layout.fragment_bubble_game_running

    override fun obtainViewModel() = bubbleGameViewModel

    companion object {
        fun newInstance(hostFragment: BubbleGameHostFragment): BubbleGameRunningFragment {
            return BubbleGameRunningFragment().apply {
                gameStateListener = hostFragment
                onBackPressedListener = hostFragment
            }
        }
    }
}