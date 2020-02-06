package ru.chernakov.feature_appfeatures.data.repository

import ru.chernakov.feature_appfeatures.R
import ru.chernakov.feature_appfeatures.data.model.AppFeature

class AppFeaturesRepository {
    fun getAppFeaturesList(): List<AppFeature> {
        return listOf(
            AppFeature(AppFeature.BUBBLE_GAME_ID, R.string.app_feature_bubble_game, R.drawable.ic_bubble_game),
            AppFeature(AppFeature.MOVIES_ID, R.string.app_feature_movies, R.drawable.ic_movies)
        )
    }
}