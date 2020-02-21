package ru.chernakov.rocketscienceapp.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OptionItem(var action: Action, @StringRes var titleRes: Int, @DrawableRes var iconRes: Int) {

    sealed class Action {
        object OpenApp : Action()
        object OpenMarket : Action()
        object DeleteApp : Action()
        object ShareApk : Action()
        class SaveApk(val isSaved: Boolean) : Action()
    }
}