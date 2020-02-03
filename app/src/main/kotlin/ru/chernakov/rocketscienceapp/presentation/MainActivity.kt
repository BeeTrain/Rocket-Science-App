package ru.chernakov.rocketscienceapp.presentation

import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.core_ui.extension.android.view.visibleOrGone
import ru.chernakov.core_ui.presentation.activity.BaseActivity
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.navigation.MainNavigator

class MainActivity : BaseActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private val mainNavigator: MainNavigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainNavigator.bind(this)
        bottomNavigation.setOnNavigationItemSelectedListener {
            it.isChecked = true
            mainViewModel.setSelectedNavigationItem(it.itemId)

            true
        }
        mainViewModel.selectedNavigationItemEvent.observe(this, Observer {
            startFlowFragment(it)
        })
        onFirstStart()
    }

    private fun onFirstStart() {
        if (mainViewModel.selectedNavigationItemEvent.value == null && mainViewModel.getUser() != null) {
            bottomNavigation.selectedItemId = R.id.navigation_appfeatures
        }
    }

    private fun startFlowFragment(it: Int?) {
        when (it) {
            R.id.navigation_favorite -> mainNavigator.openFavorite()
            R.id.navigation_profile -> mainNavigator.openProfile()
            R.id.navigation_appfeatures -> mainNavigator.openAppFeatures()
            else -> {
            }
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
}