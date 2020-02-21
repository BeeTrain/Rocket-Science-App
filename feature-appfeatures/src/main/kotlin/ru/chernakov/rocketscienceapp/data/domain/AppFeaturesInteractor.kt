package ru.chernakov.rocketscienceapp.data.domain

import ru.chernakov.rocketscienceapp.data.repository.AppFeaturesRepository

class AppFeaturesInteractor(private val appFeaturesRepository: AppFeaturesRepository) {

    fun getAppFeatures() = appFeaturesRepository.getAppFeaturesList()
}