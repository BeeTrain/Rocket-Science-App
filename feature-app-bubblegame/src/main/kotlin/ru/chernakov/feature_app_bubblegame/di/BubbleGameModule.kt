package ru.chernakov.feature_app_bubblegame.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.chernakov.feature_app_bubblegame.presentation.ui.BubbleGameViewModel

val bubbleGameModule = module {
    viewModel { BubbleGameViewModel() }
}