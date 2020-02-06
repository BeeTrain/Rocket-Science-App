package ru.chernakov.feature_appfeatures.data.domain

import ru.chernakov.feature_appfeatures.data.repository.AppFeaturesRepository

class AppFeaturesInteractor(private val appFeaturesRepository: AppFeaturesRepository) {

    fun getAppFeatures() = appFeaturesRepository.getAppFeaturesList()
}