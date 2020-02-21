package ru.chernakov.rocketscienceapp.domain

import ru.chernakov.rocketscienceapp.data.model.ApplicationItem
import ru.chernakov.rocketscienceapp.data.repository.ApplicationsRepository

class FetchAppsInteractor(private val applicationsRepository: ApplicationsRepository) {

    fun fetchApplications(): List<ApplicationItem> {
        return applicationsRepository.fetchApplications()
    }
}