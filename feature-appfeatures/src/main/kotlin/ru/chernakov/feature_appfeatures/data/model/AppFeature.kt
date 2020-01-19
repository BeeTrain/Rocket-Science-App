package ru.chernakov.feature_appfeatures.data.model

import androidx.annotation.DrawableRes
import ru.chernakov.feature_appfeatures.R

data class AppFeature(
    var id: Long,
    var name: String,
    @DrawableRes var iconRes: Int = R.drawable.ic_feature_default
) {

    companion object {
        const val BUBBLE_GAME_ID = 0L
    }
}