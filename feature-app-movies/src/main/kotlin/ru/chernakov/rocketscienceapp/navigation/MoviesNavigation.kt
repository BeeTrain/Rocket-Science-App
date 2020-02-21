package ru.chernakov.rocketscienceapp.navigation

interface MoviesNavigation {
    fun fromMoviesToAppFeatures()
    fun fromMoviesToDetails(movieJson: String)
}