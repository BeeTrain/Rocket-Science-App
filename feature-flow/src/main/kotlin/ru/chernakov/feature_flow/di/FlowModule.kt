package ru.chernakov.feature_flow.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.chernakov.feature_flow.presentation.FlowViewModel

val flowViewModule = module {
    viewModel { FlowViewModel() }
}