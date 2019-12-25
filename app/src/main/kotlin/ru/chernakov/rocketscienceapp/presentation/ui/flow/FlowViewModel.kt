package ru.chernakov.rocketscienceapp.presentation.ui.flow

import androidx.lifecycle.MutableLiveData
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel

class FlowViewModel : BaseViewModel() {
    var selectedNavigationData = MutableLiveData<Int>()

    fun setSelected(itemId: Int) {
        selectedNavigationData.postValue(itemId)
    }
}