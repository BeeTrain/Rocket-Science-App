package ru.chernakov.rocketscienceapp.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.chernakov.core_ui.extension.androidx.fragment.app.replaceFragment

class NavigationController(private val activity: AppCompatActivity) {

    fun navigate(fragment: Fragment, isAddToBackStack: Boolean = true, animation: NavigationAnimation? = null) {
        activity.supportFragmentManager.replaceFragment(fragment).apply {
            addToBackStack = isAddToBackStack
            animation?.let {
                enter = it.enter
                exit = it.exit
                popEnter = it.popEnter
                popExit = it.popExit
            }
            commit()
        }
    }
}