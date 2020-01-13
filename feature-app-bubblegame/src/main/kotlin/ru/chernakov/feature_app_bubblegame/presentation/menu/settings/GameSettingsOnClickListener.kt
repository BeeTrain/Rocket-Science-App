package ru.chernakov.feature_app_bubblegame.presentation.menu.settings

import ru.chernakov.feature_app_bubblegame.presentation.menu.settings.GameSettingsModel

interface GameSettingsOnClickListener {
    fun onApply(gameSettingsModel: GameSettingsModel)
}