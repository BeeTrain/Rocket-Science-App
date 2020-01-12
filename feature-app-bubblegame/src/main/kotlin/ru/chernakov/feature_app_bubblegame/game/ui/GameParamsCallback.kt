package ru.chernakov.feature_app_bubblegame.game.ui

import ru.chernakov.feature_app_bubblegame.game.game.Game

/**
 * @author mikhail.funikov@e-legion.com on 12/06/2017.
 */

interface GameParamsCallback {
    fun onSettingsParamsSet(params: Game.Params)
    fun onScreenParamsSet(params: Game.Params)
    fun onParamsReset()
}