package ru.chernakov.feature_app_bubblegame.game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import ru.chernakov.feature_app_bubblegame.data.GameTime
import kotlinx.android.synthetic.main.fragment_game_start.*
import ru.chernakov.feature_app_bubblegame.R
import ru.chernakov.feature_app_bubblegame.game.game.Game
import ru.chernakov.feature_app_bubblegame.data.GameSpeed

/**
 * @author mikhail.funikov@e-legion.com on 11/06/2017.
 */

class GameStartFragment : GameFragment(), SeekBar.OnSeekBarChangeListener {
    companion object {
        fun newInstance(): GameStartFragment {
            return GameStartFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
    }

    private fun initUi() {
        initCircleCountUI()
        initGameSpeedUI()
        initGameTimeUI()

        bStartGame.setOnClickListener {
            val params = Game.obtainParams()
            params.circleCount = sbCirclesCount.progress + 3
            params.gameSpeed = GameSpeed.values()[sGameSpeed.selectedItemPosition]
            params.gameTime = GameTime.values()[sGameTime.selectedItemPosition]

            gameParamsCallback.onSettingsParamsSet(params)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        updateCircleCountLabel()
    }

    private fun initCircleCountUI() {
        updateCircleCountLabel()
        sbCirclesCount.setOnSeekBarChangeListener(this)
    }

    private fun initGameSpeedUI() {
        sGameSpeed.adapter = ArrayAdapter<GameSpeed>(activity, android.R.layout.simple_list_item_1, android.R.id.text1, GameSpeed.values())
    }

    private fun initGameTimeUI() {
        val gameTimeValues = mutableListOf<String>()
        for (time in GameTime.values()) {
            val value = getString(R.string.game_speed_value, time.toString(), time.timeMs / 1000)
            gameTimeValues.add(value)
        }

        sGameTime.adapter = ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, android.R.id.text1, gameTimeValues)
    }

    private fun updateCircleCountLabel() {
        val progress = sbCirclesCount.progress
        tvCirclesLabel.text = getString(R.string.circles_count, progress + 3)
    }
}