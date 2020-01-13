package ru.chernakov.feature_app_bubblegame.presentation.menu

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.d_fragment_bubble_game_settings.*
import ru.chernakov.core_ui.presentation.fragment.BaseBottomSheetDialog
import ru.chernakov.feature_app_bubblegame.R
import ru.chernakov.feature_app_bubblegame.data.GameSpeed
import ru.chernakov.feature_app_bubblegame.data.GameTime

class BubbleGameSettingsDialog : BaseBottomSheetDialog(), SeekBar.OnSeekBarChangeListener {
    override val layoutResId = R.layout.d_fragment_bubble_game_settings
    private var gameSettingsOnClickListener: GameSettingsOnClickListener? = null

    lateinit var gameSettings: GameSettings

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
            gameSettingsOnClickListener?.onApply(gameSettings)
            dismiss()
        }
    }

    private fun updateGameSettings() {
        gameSettings.gameSpeed = GameSpeed.values()[spGameSpeed.selectedItemPosition]
        gameSettings.gameTime = GameTime.values()[spGameTime.selectedItemPosition]
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        updateBubbleCountLabel()
    }

    private fun initBubbleCountUI() {
        sbBubblesCount.progress = gameSettings.bubbleCount - MIN_BUBBLES_COUNT
        updateBubbleCountLabel()
        sbBubblesCount.setOnSeekBarChangeListener(this)
    }

    private fun initGameSpeedUI() {
        var selected = 0
        for (speed in GameSpeed.values()) {
            if (speed == gameSettings.gameSpeed) {
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
        var selected = 0
        for (time in GameTime.values()) {
            val value = getString(R.string.game_speed_value, time.toString(), time.timeMs / 1000)
            gameTimeValues.add(value)
            if (time == gameSettings.gameTime) {
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
        gameSettings.bubbleCount = sbBubblesCount.progress + MIN_BUBBLES_COUNT
        tvBubblesCount.text = getString(R.string.bubbles_count, gameSettings.bubbleCount)
    }

    companion object {
        private const val MIN_BUBBLES_COUNT = 3

        fun show(fm: FragmentManager, gameSettings: GameSettings) {
            val dialog = BubbleGameSettingsDialog().apply {
                this.gameSettings = gameSettings
            }

            dialog.show(fm, BubbleGameSettingsDialog::class.java.toString())
        }
    }
}