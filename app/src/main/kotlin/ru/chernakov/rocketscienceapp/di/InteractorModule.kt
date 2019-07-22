package ru.chernakov.rocketscienceapp.di

import org.koin.dsl.module
import ru.chernakov.rocketscienceapp.domain.interactor.chat.PrepareMessageInteractor

val interactorModule = module {
    factory { PrepareMessageInteractor() }
}