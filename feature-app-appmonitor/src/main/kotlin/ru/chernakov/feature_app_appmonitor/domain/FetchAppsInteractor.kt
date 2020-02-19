package ru.chernakov.feature_app_appmonitor.domain

import ru.chernakov.feature_app_appmonitor.data.model.ApplicationItem
import ru.chernakov.feature_app_appmonitor.data.repository.ApplicationsRepository

class FetchAppsInteractor(private val applicationsRepository: ApplicationsRepository) {

    fun fetchApplications(): List<ApplicationItem> {
        return applicationsRepository.fetchApplications()
    }
}