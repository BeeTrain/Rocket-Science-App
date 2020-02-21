package ru.chernakov.rocketscienceapp.domain

import ru.chernakov.rocketscienceapp.data.model.ApplicationItem
import ru.chernakov.rocketscienceapp.data.repository.ApplicationsRepository

class LoadAppInfoInteractor(private val applicationsRepository: ApplicationsRepository) {

    fun loadAppInfo(appPackage: String): ApplicationItem? {
        return applicationsRepository.getApplicationInfo(appPackage)
    }
}