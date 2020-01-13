package ru.chernakov.feature_app_bubblegame.presentation.host

import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel
import ru.chernakov.feature_app_bubblegame.domain.BubbleGameInteractor

class BubbleGameViewModel(val gameInteractor: BubbleGameInteractor): BaseViewModel()