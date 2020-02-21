package ru.chernakov.rocketscienceapp.navigation

interface AppMonitorNavigation {
    fun fromAppMonitorToAppFeatures()
    fun fromAppsListToInfo(packageId: String)
}