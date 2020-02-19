package ru.chernakov.feature_app_appmonitor.domain

import ru.chernakov.feature_app_appmonitor.data.model.ApplicationItem
import ru.chernakov.feature_app_appmonitor.data.repository.ApplicationsRepository

class LoadAppInfoInteractor(private val applicationsRepository: ApplicationsRepository) {

    fun loadAppInfo(appPackage: String): ApplicationItem? {
        return applicationsRepository.getApplicationInfo(appPackage)
    }
}