package ru.chernakov.rocketscienceapp.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.chernakov.core_ui.extension.androidx.fragment.app.replaceFragment
import ru.chernakov.feature_app_bubblegame.game.ui.BubbleGameActivity
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.presentation.MainActivity

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

    fun subNavigate(fragment: Fragment, isAddToBackStack: Boolean = true, animation: NavigationAnimation? = null) {
        activity.supportFragmentManager.replaceFragment(fragment, R.id.subContainer).apply {
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

    fun restartHost() {
        val intent = MainActivity.makeIntent(activity)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
    }

    fun startBubbleGame() {
        activity.startActivity(Intent(activity, BubbleGameActivity::class.java))
    }
}