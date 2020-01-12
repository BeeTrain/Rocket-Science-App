package ru.chernakov.feature_app_bubblegame.game.ui

import android.content.Context
import androidx.fragment.app.Fragment
import ru.chernakov.feature_app_bubblegame.game.game.Game

/**
 * @author mikhail.funikov@e-legion.com on 11/06/2017.
 */

open class GameFragment: Fragment() {

    lateinit var gameParamsCallback: GameParamsCallback
        private set

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (activity !is Game.StatusCallback) {
            throw IllegalStateException("activity must implement game status callback interface!")
        }

        gameParamsCallback = activity as GameParamsCallback
    }
}