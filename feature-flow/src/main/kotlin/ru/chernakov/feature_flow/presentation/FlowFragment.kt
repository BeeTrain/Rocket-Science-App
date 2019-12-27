package ru.chernakov.feature_flow.presentation

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_flow.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.core_base.util.lifecycle.SafeObserver
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel
import ru.chernakov.feature_flow.R
import ru.chernakov.feature_flow.navigation.FlowNavigation

class FlowFragment : BaseFragment() {
    private val flowViewModel: FlowViewModel by viewModel()
    private val navigator: FlowNavigation by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        flowViewModel.selectedNavigationData.observe(viewLifecycleOwner, SafeObserver {
            startFlowFragment(it)
        })

        if (savedInstanceState == null) {
            navigation.selectedItemId = R.id.navigation_appfeatures
        }
    }

    private fun setupNavigation() {
        navigation.setOnNavigationItemSelectedListener {
            it.isChecked = true
            flowViewModel.setSelected(it.itemId)
            false
        }
    }

    private fun startFlowFragment(itemId: Int) {
        when (itemId) {
            R.id.navigation_profile -> navigator.openProfile()
            R.id.navigation_appfeatures -> navigator.openList()
            R.id.navigation_organization -> {
            }
        }
    }

    override fun getLayout(): Int = R.layout.fragment_flow

    override fun obtainViewModel(): BaseViewModel = flowViewModel

    companion object {
        fun newInstance() = FlowFragment()
    }
}