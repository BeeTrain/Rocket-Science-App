package ru.chernakov.rocketscienceapp.presentation

import androidx.lifecycle.MutableLiveData
import ru.chernakov.rocketscienceapp.data.domain.AppFeaturesInteractor
import ru.chernakov.rocketscienceapp.data.model.AppFeature
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel

class AppFeaturesViewModel(private val appFeaturesInteractor: AppFeaturesInteractor) : BaseViewModel() {
    val appFeaturesLiveData = MutableLiveData<List<AppFeature>>()

    init {
        loadAppFeatures()
    }

    fun loadAppFeatures() {
        appFeaturesLiveData.value = appFeaturesInteractor.getAppFeatures()
    }
}