package ru.chernakov.feature_app_appmonitor.presentation.list

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_apps_list.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.core_base.util.lifecycle.SafeObserver
import ru.chernakov.core_ui.extension.android.view.visibleOrGone
import ru.chernakov.core_ui.extension.android.view.visibleOrInvisible
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.feature_app_appmonitor.R
import ru.chernakov.feature_app_appmonitor.navigation.AppMonitorNavigation
import ru.chernakov.feature_app_appmonitor.presentation.list.adapter.AppsListAdapter

class AppsListFragment : BaseFragment() {
    private val appsListViewModel: AppsListViewModel by viewModel()
    private val navigator: AppMonitorNavigation by inject()

    private val appsAdapter by lazy { AppsListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigator.fromAppMonitorToAppFeatures()
                }
            }
        )
        appsAdapter.apply {
            onItemClickListener = {
                navigator.fromAppsListToInfo(it.appPackage)
            }
        }
        rvApps.apply {
            adapter = appsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
        }

        appsListViewModel.applicationsData.observe(viewLifecycleOwner, SafeObserver {
            appsAdapter.setData(it)
        })
        appsListViewModel.loading.observe(viewLifecycleOwner, SafeObserver {
            pbLoading.visibleOrGone(it)
            content.visibleOrInvisible(!it)
        })
    }

    override fun getLayout() = R.layout.fragment_apps_list

    override fun obtainViewModel() = appsListViewModel
}