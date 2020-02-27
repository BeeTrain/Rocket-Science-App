package ru.chernakov.rocketscienceapp.presentation

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.extension.android.view.visibleOrGone
import ru.chernakov.rocketscienceapp.navigation.MainNavigator
import ru.chernakov.rocketscienceapp.presentation.activity.BaseActivity
import ru.chernakov.rocketscienceapp.util.lifecycle.SafeObserver

class MainActivity : BaseActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private val mainNavigator: MainNavigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainNavigator.bind(this)
        mainViewModel.selectedNavigationItemEvent.observe(this, SafeObserver {
            startFlowFragment(it)
        })

        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        // Set default selected menu item
        if (mainViewModel.selectedNavigationItemEvent.value == null) {
            bottomNavigation.selectedItemId = DEFAULT_MENU_ITEM_ID
        }
        bottomNavigation.setOnNavigationItemSelectedListener {
            mainViewModel.setSelectedNavigationItem(it.itemId)
        }
        // Open default screen if user logged in
        if (mainViewModel.isFirstLaunchWithUser) {
            bottomNavigation.selectedItemId = DEFAULT_MENU_ITEM_ID
        }
    }

    private fun startFlowFragment(it: Int) {
        when (it) {
            R.id.navigation_favorite -> mainNavigator.openFavorite()
            R.id.navigation_profile -> mainNavigator.openProfile()
            R.id.navigation_appfeatures -> mainNavigator.openAppFeatures()
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

    fun setBottomNavigationVisibility(isVisible: Boolean) {
        bottomNavigation.visibleOrGone(isVisible)
    }

    override fun onSupportNavigateUp(): Boolean {
        return mainNavigator.navigation?.navigateUp() ?: false || super.onSupportNavigateUp()
    }

    override fun getLayout(): Int = R.layout.activity_main

    companion object {
        private val DEFAULT_MENU_ITEM_ID = R.id.navigation_appfeatures
    }
}