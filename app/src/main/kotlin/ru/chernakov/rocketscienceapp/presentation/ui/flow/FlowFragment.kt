package ru.chernakov.rocketscienceapp.presentation.ui.flow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_flow.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.extension.androidx.fragment.app.replaceFragment
import ru.chernakov.rocketscienceapp.presentation.ui.base.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.presentation.ui.base.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.feed.FeedFragment
import ru.chernakov.rocketscienceapp.presentation.ui.profile.ProfileFragment

class FlowFragment : BaseFragment() {
    private val viewModel: FlowViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() {
        navigation.setOnNavigationItemSelectedListener {
            it.isChecked = true
            when (it.itemId) {
                R.id.navigation_profile -> startFlowFragment(ProfileFragment.newInstance())
                R.id.navigation_feed -> startFlowFragment(FeedFragment.newInstance())
                R.id.navigation_organization -> {
                }
            }
            false
        }
    }

    private fun startFlowFragment(fragment: Fragment, isAddToBackStack: Boolean = false) {
        activity?.let {
            it.supportFragmentManager.replaceFragment(fragment, R.id.containerFlow).apply {
                addToBackStack = isAddToBackStack
                commit()
            }
        }
    }

    override fun getLayout(): Int = R.layout.fragment_flow

    override fun obtainViewModel(): BaseViewModel = viewModel

    companion object {

        fun newInstance() = FlowFragment()
    }
}