package ru.chernakov.feature_app_bubblegame.presentation.menu.settings

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.d_fragment_bubble_game_settings.*
import ru.chernakov.feature_app_bubblegame.R
import ru.chernakov.feature_app_bubblegame.data.GameSpeed
import ru.chernakov.feature_app_bubblegame.data.GameTime
import ru.chernakov.rocketscienceapp.presentation.fragment.BaseBottomSheetDialog
import java.util.concurrent.TimeUnit

class BubbleGameSettingsDialog : BaseBottomSheetDialog(), SeekBar.OnSeekBarChangeListener {
    override val layoutResId = R.layout.d_fragment_bubble_game_settings
    private var gameSettingsOnClickListener: GameSettingsOnClickListener? = null

    lateinit var gameSettingsModel: GameSettingsModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        resolveCallback(context)
    }

    private fun resolveCallback(context: Context?) {
        if (parentFragment is GameSettingsOnClickListener) {
            gameSettingsOnClickListener = parentFragment as GameSettingsOnClickListener
        } else if (context is GameSettingsOnClickListener) {
            gameSettingsOnClickListener = context
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initBubbleCountUI()
        initGameSpeedUI()
        initGameTimeUI()

        btApply.setOnClickListener {
            updateGameSettings()
            gameSettingsOnClickListener?.onApply(gameSettingsModel)
            dismiss()
        }
    }

    private fun updateGameSettings() {
        gameSettingsModel.gameSpeed = GameSpeed.values()[spGameSpeed.selectedItemPosition]
        gameSettingsModel.gameTime = GameTime.values()[spGameTime.selectedItemPosition]
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        // do Nothing
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        // do Nothing
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        updateBubbleCountLabel()
    }

    private fun initBubbleCountUI() {
        sbBubblesCount.progress = gameSettingsModel.bubbleCount - MIN_BUBBLES_COUNT
        updateBubbleCountLabel()
        sbBubblesCount.setOnSeekBarChangeListener(this)
    }

    private fun initGameSpeedUI() {
        var selected = FIRST_ITEM_INDEX
        for (speed in GameSpeed.values()) {
            if (speed == gameSettingsModel.gameSpeed) {
                selected = speed.ordinal
            }
        }

        spGameSpeed.adapter = ArrayAdapter<GameSpeed>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            GameSpeed.values()
        )

        spGameSpeed.setSelection(selected)
    }

    private fun initGameTimeUI() {
        val gameTimeValues = mutableListOf<String>()
        var selected = FIRST_ITEM_INDEX
        for (time in GameTime.values()) {
            val value = getString(
                R.string.game_speed_value, time.toString(), TimeUnit.MILLISECONDS.toSeconds(time.timeMs)
            )
            gameTimeValues.add(value)
            if (time == gameSettingsModel.gameTime) {
                selected = time.ordinal
            }
        }

        spGameTime.adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            gameTimeValues
        )
        spGameTime.setSelection(selected)
    }

    private fun updateBubbleCountLabel() {
        gameSettingsModel.bubbleCount = sbBubblesCount.progress + MIN_BUBBLES_COUNT
        tvBubblesCount.text = getString(R.string.bubbles_count, gameSettingsModel.bubbleCount)
    }

    companion object {
        private const val MIN_BUBBLES_COUNT = 3
        private const val FIRST_ITEM_INDEX = 0

        fun show(fm: FragmentManager, gameSettingsModel: GameSettingsModel) {
            val dialog = BubbleGameSettingsDialog().apply {
                this.gameSettingsModel = gameSettingsModel
            }

            dialog.show(fm, BubbleGameSettingsDialog::class.java.toString())
        }
    }
}