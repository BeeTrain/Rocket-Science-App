package ru.chernakov.feature_app_bubblegame.presentation.menu

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_bubble_game_menu.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.core_base.util.lifecycle.SafeObserver
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.feature_app_bubblegame.R
import ru.chernakov.feature_app_bubblegame.data.GameStatus
import ru.chernakov.feature_app_bubblegame.navigation.BubbleGameNavigation
import ru.chernakov.feature_app_bubblegame.presentation.BubbleGameViewModel
import ru.chernakov.feature_app_bubblegame.presentation.menu.settings.BubbleGameSettingsDialog
import ru.chernakov.feature_app_bubblegame.presentation.menu.settings.GameSettingsModel
import ru.chernakov.feature_app_bubblegame.presentation.menu.settings.GameSettingsOnClickListener

class BubbleGameMenuFragment : BaseFragment(), GameSettingsOnClickListener {
    private val bubbleGameViewModel: BubbleGameViewModel by viewModel()
    private val navigator: BubbleGameNavigation by inject()

    private var gameSettings = GameSettingsModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btStartGame.setOnClickListener {
            bubbleGameViewModel.gameInteractor.bubbleCount = gameSettings.bubbleCount
            bubbleGameViewModel.gameInteractor.speed = gameSettings.gameSpeed
            bubbleGameViewModel.gameInteractor.gameTime = gameSettings.gameTime
            bubbleGameViewModel.gameInteractor.updateStatus(GameStatus.RUNNING)
        }
        btSettings.setOnClickListener {
            BubbleGameSettingsDialog.show(childFragmentManager, gameSettings)
        }
        bubbleGameViewModel.gameInteractor.status.observe(viewLifecycleOwner, SafeObserver {
            when (it) {
                GameStatus.RUNNING -> {
                    navigator.startBubbleGame()
                }
                else -> {
                }
            }
        })
    }

    override fun onApply(gameSettingsModel: GameSettingsModel) {
        this.gameSettings = gameSettingsModel
    }

    override fun getLayout() = R.layout.fragment_bubble_game_menu

    override fun obtainViewModel() = bubbleGameViewModel
}