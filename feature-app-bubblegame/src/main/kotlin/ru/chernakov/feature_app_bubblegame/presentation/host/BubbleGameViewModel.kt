package ru.chernakov.feature_app_bubblegame.presentation.host

import ru.chernakov.feature_app_bubblegame.domain.BubbleGameInteractor
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel

class BubbleGameViewModel(val gameInteractor: BubbleGameInteractor) : BaseViewModel()