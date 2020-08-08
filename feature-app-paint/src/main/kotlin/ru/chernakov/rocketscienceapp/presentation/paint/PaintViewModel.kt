package ru.chernakov.rocketscienceapp.presentation.paint

import androidx.annotation.ColorInt
import androidx.lifecycle.MutableLiveData
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel

class PaintViewModel : BaseViewModel() {
    val selectedColorResData = MutableLiveData<Int>()

    fun initColorDefaultValue(@ColorInt color: Int) {
        if (selectedColorResData.value == null) {
            selectedColorResData.value = color
        }
    }

    fun setSelectedColorRes(@ColorInt color: Int) {
        selectedColorResData.value = color
    }
}