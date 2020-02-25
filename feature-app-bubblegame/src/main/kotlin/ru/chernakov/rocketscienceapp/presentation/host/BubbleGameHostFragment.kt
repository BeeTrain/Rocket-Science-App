package ru.chernakov.rocketscienceapp.presentation.host

import android.os.Bundle
import android.view.View
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.bubblegame.R
import ru.chernakov.rocketscienceapp.data.GameStatus
import ru.chernakov.rocketscienceapp.navigation.BubbleGameNavigation
import ru.chernakov.rocketscienceapp.navigation.OnBackPressedListener
import ru.chernakov.rocketscienceapp.presentation.widget.BubbleGameStateListener
import ru.chernakov.rocketscienceapp.presentation.widget.BubbleGameStatusListener
import ru.chernakov.rocketscienceapp.presentation.fragment.BaseFragment

class BubbleGameHostFragment : BaseFragment(), OnBackPressedListener, BubbleGameStatusListener,
    BubbleGameStateListener {
    private val bubbleGameViewModel: BubbleGameViewModel by viewModel()
    private val navigator: BubbleGameNavigation by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bubbleGameViewModel.gameInteractor.statusCallback = this
    }

    override fun onDestroy() {
        super.onDestroy()
        bubbleGameViewModel.gameInteractor.statusCallback = null
    }

    override fun onGameStatusChanged(gameStatus: GameStatus) {
        when (gameStatus) {
            GameStatus.STOPPED -> navigator.openBubbleGameMenu(this)
            GameStatus.RUNNING -> navigator.startBubbleGame(this)
            GameStatus.WIN, GameStatus.LOSS -> navigator.openBubbleGameResult(this)
        }
    }

    override fun onSettingsSet() {
        bubbleGameViewModel.gameInteractor.updateStatus(GameStatus.RUNNING)
    }

    override fun onScreenParamsSet() {
        bubbleGameViewModel.gameInteractor.start()
    }

    override fun onSettingsReset() {
        bubbleGameViewModel.gameInteractor.updateStatus(GameStatus.STOPPED)
    }

    override fun onRunningBackPressed() {
        bubbleGameViewModel.gameInteractor.updateStatus(GameStatus.STOPPED)
        navigator.openBubbleGameMenu(this)
    }

    override fun onMenuBackPressed() {
        navigator.fromBubbleGameToAppFeatures()
    }

    override fun onResultBackPressed() {
        navigator.openBubbleGameMenu(this)
    }

    override fun getLayout() = R.layout.fragment_bubble_game

    override fun obtainViewModel() = bubbleGameViewModel
}