package ru.chernakov.rocketscienceapp.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.chernakov.rocketscienceapp.appfeatures.R

data class AppFeature(
    var id: Long,
    @StringRes var name: Int = R.string.app_feature_name_default,
    @DrawableRes var iconRes: Int = R.drawable.ic_feature_default
) {

    companion object {
        const val BUBBLE_GAME_ID = 0L
        const val MOVIES_ID = 1L
        const val APPMONITOR_ID = 2L
    }
}