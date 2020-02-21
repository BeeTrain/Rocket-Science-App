package ru.chernakov.rocketscienceapp.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.chernakov.rocketscienceapp.domain.BubbleGameInteractor
import ru.chernakov.rocketscienceapp.presentation.host.BubbleGameViewModel
import ru.chernakov.rocketscienceapp.util.BubblePositionUtil
import ru.chernakov.rocketscienceapp.util.TouchEventProcessor

val bubbleGameModule = module {
    single { BubbleGameInteractor(get(), get()) }

    factory { BubblePositionUtil() }
    factory { TouchEventProcessor() }

    viewModel { BubbleGameViewModel(get()) }
}