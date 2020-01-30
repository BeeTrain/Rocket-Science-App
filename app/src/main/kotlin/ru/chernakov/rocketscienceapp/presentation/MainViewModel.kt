package ru.chernakov.rocketscienceapp.presentation

import ru.chernakov.core_base.util.lifecycle.SingleLiveEvent
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel

class MainViewModel : BaseViewModel() {
    val selectedNavigationItemEvent = SingleLiveEvent<Int>()

    fun setSelectedNavigationItem(itemId: Int) {
        if (selectedNavigationItemEvent.value != itemId) selectedNavigationItemEvent.value = itemId
    }
}