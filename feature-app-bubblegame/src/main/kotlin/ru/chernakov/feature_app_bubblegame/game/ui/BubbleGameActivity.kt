package ru.chernakov.feature_app_bubblegame.game.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.chernakov.feature_app_bubblegame.R
import ru.chernakov.feature_app_bubblegame.data.GameStatus
import ru.chernakov.feature_app_bubblegame.game.game.Game

class BubbleGameActivity : AppCompatActivity(), Game.StatusCallback, GameParamsCallback {
    private var game: Game? = null

    private var prevGameStatus: GameStatus? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(ru.chernakov.core_ui.R.style.AppTheme)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        refreshUI()
    }

    override fun onBackPressed() {
        val fragment = this.supportFragmentManager.findFragmentById(R.id.container)
        if (fragment !is GameStartFragment) {
            replaceFragment(GameStartFragment.newInstance())
        } else {
            super.onBackPressed()
        }
    }

    override fun onGameStatusChanged(game: Game, gameStatus: GameStatus) {
        this.game = game

        refreshUI()
    }

    private fun refreshUI() {
        val gameStatus = game?.status ?: GameStatus.STOPPED

        if (gameStatus == prevGameStatus) {
            return
        }

        prevGameStatus = gameStatus
        val fragment: Fragment = when (gameStatus) {
            GameStatus.STOPPED -> GameStartFragment.newInstance()
            GameStatus.RUNNING -> GameRunningFragment.newInstance()
            GameStatus.LOSS, GameStatus.WIN -> GameResultFragment.newInstance()
        }

        replaceFragment(fragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    override fun onScreenParamsSet(params: Game.Params) {
        Game.startGame().statusCallback = this
    }

    override fun onParamsReset() {
        replaceFragment(GameStartFragment.newInstance())
    }

    override fun onSettingsParamsSet(params: Game.Params) {
        replaceFragment(GameRunningFragment.newInstance())
    }
}
