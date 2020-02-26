package ru.chernakov.rocketscienceapp.navigation

interface AuthNavigation {
    fun fromLoginToRegister()
    fun fromLoginToAppFeatures()
    fun fromRegisterToAppFeatures()
    fun fromRegisterToLogin()
}