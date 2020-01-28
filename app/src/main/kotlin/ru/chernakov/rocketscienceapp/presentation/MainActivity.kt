package ru.chernakov.rocketscienceapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import ru.chernakov.core_ui.extension.android.view.visibleOrGone
import ru.chernakov.core_ui.presentation.activity.BaseActivity
import ru.chernakov.core_ui.util.navigation.setupWithNavController
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.navigation.MainNavigator

class MainActivity : BaseActivity() {
    private val mainNavigator: MainNavigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainNavigator.bind(this)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    override fun onPause() {
        super.onPause()
        mainNavigator.unbind()
    }

    override fun onResume() {
        super.onResume()
        mainNavigator.bind(this)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(
            R.navigation.navigation_appfeatures,
            R.navigation.navigation_favorite,
            R.navigation.navigation_profile
        )

        bottomNavigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )
    }

    fun setBottomNavigationVisibility(isVisible: Boolean) {
        bottomNavigation.visibleOrGone(isVisible)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_container)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun getLayout(): Int = R.layout.activity_main

    companion object {
        fun makeIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}