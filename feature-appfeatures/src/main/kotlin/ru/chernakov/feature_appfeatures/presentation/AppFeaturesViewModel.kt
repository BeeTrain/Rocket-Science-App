package ru.chernakov.feature_appfeatures.presentation

import androidx.lifecycle.MutableLiveData
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel
import ru.chernakov.feature_appfeatures.data.domain.AppFeaturesInteractor
import ru.chernakov.feature_appfeatures.data.model.AppFeature

class AppFeaturesViewModel(private val appFeaturesInteractor: AppFeaturesInteractor) : BaseViewModel() {
    val appFeaturesLiveData = MutableLiveData<List<AppFeature>>()

    init {
        loadAppFeatures()
    }

    fun loadAppFeatures() {
        appFeaturesLiveData.value = appFeaturesInteractor.getAppFeatures()
    }
}