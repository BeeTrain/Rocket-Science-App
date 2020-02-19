package ru.chernakov.feature_app_appmonitor.presentation.list

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel
import ru.chernakov.feature_app_appmonitor.data.model.ApplicationItem
import ru.chernakov.feature_app_appmonitor.domain.FetchAppsInteractor

class AppsListViewModel(private val fetchAppsInteractor: FetchAppsInteractor) : BaseViewModel() {
    val applicationsData = MutableLiveData<List<ApplicationItem>>()

    init {
        loading.value = true
        fetchApplications()
    }

    private fun fetchApplications() {
        launchLoadingErrorJob(Dispatchers.IO) {
            applicationsData.postValue(fetchAppsInteractor.fetchApplications())
        }
    }
}