package ru.chernakov.rocketscienceapp.navigation

import androidx.navigation.fragment.FragmentNavigator

interface MoviesNavigation {
    fun fromMoviesToAppFeatures()
    fun fromMoviesToDetails(navigationExtras: FragmentNavigator.Extras, movieJson: String)
}