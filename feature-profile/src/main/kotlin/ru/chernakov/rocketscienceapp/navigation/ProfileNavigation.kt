package ru.chernakov.rocketscienceapp.navigation

interface ProfileNavigation {
    fun fromProfileToFavorite()
    fun fromProfileToAppFeatures()
    fun fromProfileToSettings()
    fun logoutFromSettings()
    fun fromSettingsToProfile()
}