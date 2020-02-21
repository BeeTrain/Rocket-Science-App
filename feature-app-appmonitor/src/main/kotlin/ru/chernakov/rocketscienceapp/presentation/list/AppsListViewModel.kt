package ru.chernakov.rocketscienceapp.presentation.list

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import ru.chernakov.rocketscienceapp.data.model.ApplicationItem
import ru.chernakov.rocketscienceapp.domain.FetchAppsInteractor
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel

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