package ru.chernakov.feature_app_appmonitor.navigation

interface AppMonitorNavigation {
    fun fromAppMonitorToAppFeatures()
    fun fromAppsListToInfo(packageId: String)
}