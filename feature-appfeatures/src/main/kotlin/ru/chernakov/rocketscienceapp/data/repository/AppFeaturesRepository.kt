package ru.chernakov.rocketscienceapp.data.repository

import ru.chernakov.rocketscienceapp.appfeatures.R
import ru.chernakov.rocketscienceapp.data.model.AppFeature

class AppFeaturesRepository {
    fun getAppFeaturesList(): List<AppFeature> {
        return listOf(
            AppFeature(AppFeature.BUBBLE_GAME_ID, R.string.app_feature_bubble_game, R.drawable.ic_bubble_game),
            AppFeature(AppFeature.MOVIES_ID, R.string.app_feature_movies, R.drawable.ic_movies),
            AppFeature(AppFeature.APPMONITOR_ID, R.string.app_feature_appmonitor, R.drawable.ic_appmonitor),
            AppFeature(AppFeature.PAINT_ID, R.string.app_feature_paint, R.drawable.ic_paint)
        )
    }
}