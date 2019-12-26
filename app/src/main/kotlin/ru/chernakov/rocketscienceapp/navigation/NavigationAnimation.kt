package ru.chernakov.rocketscienceapp.navigation

import androidx.annotation.AnimRes

data class NavigationAnimation(
    @AnimRes val enter: Int = 0,
    @AnimRes val exit: Int = 0,
    @AnimRes val popEnter: Int = 0,
    @AnimRes val popExit: Int = 0
)