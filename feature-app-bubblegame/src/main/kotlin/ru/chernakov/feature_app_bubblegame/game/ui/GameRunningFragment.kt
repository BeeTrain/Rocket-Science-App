package ru.chernakov.feature_app_bubblegame.game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_game_running.*
import ru.chernakov.feature_app_bubblegame.R

/**
 * @author mikhail.funikov@e-legion.com on 11/06/2017.
 */

class GameRunningFragment: GameFragment() {

    companion object {
        fun newInstance(): Fragment {
            return GameRunningFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_running, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gsGameCanvas.calculateParamsAndDrawGame(gameParamsCallback)
    }
}