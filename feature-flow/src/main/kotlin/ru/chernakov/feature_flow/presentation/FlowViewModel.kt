package ru.chernakov.feature_flow.presentation

import ru.chernakov.core_base.util.lifecycle.SingleLiveEvent
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel

class FlowViewModel : BaseViewModel() {
    var selectedScreenEvent = SingleLiveEvent<Int>()

    fun getSelectedScreenId() = selectedScreenEvent.value

    fun setSelected(itemId: Int) {
        selectedScreenEvent.postValue(itemId)
    }
}