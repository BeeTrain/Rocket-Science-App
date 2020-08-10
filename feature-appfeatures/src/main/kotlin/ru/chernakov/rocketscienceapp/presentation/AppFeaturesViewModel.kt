package ru.chernakov.rocketscienceapp.presentation

import android.content.Context
import androidx.lifecycle.MutableLiveData
import ru.chernakov.rocketscienceapp.data.domain.AppFeaturesInteractor
import ru.chernakov.rocketscienceapp.data.model.AppFeature
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel

class AppFeaturesViewModel(private val appFeaturesInteractor: AppFeaturesInteractor) : BaseViewModel() {
    val appFeaturesLiveData = MutableLiveData<List<AppFeature>>()
    val appFeaturesFilteredLiveData = MutableLiveData<List<AppFeature>>()

    init {
        loadAppFeatures()
    }

    fun loadAppFeatures() {
        appFeaturesLiveData.value = appFeaturesInteractor.getAppFeatures()
    }

    fun filterAppFeatures(context: Context, query: String) {
        val features = appFeaturesLiveData.value ?: emptyList()
        appFeaturesFilteredLiveData.value = features.filter {
            val name = context.getString(it.name)
            name.contains(query, true)
        }
    }
}