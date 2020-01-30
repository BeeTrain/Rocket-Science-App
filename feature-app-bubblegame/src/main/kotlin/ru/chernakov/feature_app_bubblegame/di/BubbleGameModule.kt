package ru.chernakov.feature_app_bubblegame.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.chernakov.feature_app_bubblegame.domain.BubbleGameInteractor
import ru.chernakov.feature_app_bubblegame.presentation.host.BubbleGameViewModel
import ru.chernakov.feature_app_bubblegame.util.BubblePositionUtil
import ru.chernakov.feature_app_bubblegame.util.TouchEventProcessor

val bubbleGameModule = module {
    single { BubbleGameInteractor(get(), get()) }

    factory { BubblePositionUtil() }
    factory { TouchEventProcessor() }

    viewModel { BubbleGameViewModel(get()) }
}