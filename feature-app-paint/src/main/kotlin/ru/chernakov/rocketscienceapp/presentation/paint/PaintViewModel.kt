package ru.chernakov.rocketscienceapp.presentation.paint

import androidx.annotation.ColorInt
import androidx.lifecycle.MutableLiveData
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel

class PaintViewModel : BaseViewModel() {
    val selectedColorLiveData = MutableLiveData<Int>()
    val menuVisibleLiveData = MutableLiveData<Boolean>()
    val selectedModeLiveData = MutableLiveData<PaintMode>()

    init {
        setMenuVisible(true)
    }

    fun initColorDefaultValue(@ColorInt color: Int) {
        if (selectedColorLiveData.value == null) {
            selectedColorLiveData.value = color
        }
    }

    fun setSelectedColor(@ColorInt color: Int) {
        selectedColorLiveData.value = color
        selectedModeLiveData.value = PaintMode.DRAW
    }

    fun setEraseMode() {
        selectedModeLiveData.value = PaintMode.ERASE
    }

    fun toggleMenu() {
        val menuState = menuVisibleLiveData.value ?: false
        setMenuVisible(!menuState)
    }

    private fun setMenuVisible(isVisible: Boolean) {
        menuVisibleLiveData.value = isVisible
    }
}