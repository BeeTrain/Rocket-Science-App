package ru.chernakov.rocketscienceapp.presentation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_appfeatures.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.rocketscienceapp.appfeatures.R
import ru.chernakov.rocketscienceapp.data.model.AppFeature
import ru.chernakov.rocketscienceapp.navigation.AppFeaturesNavigation
import ru.chernakov.rocketscienceapp.presentation.adapter.AppFeaturesAdapter
import ru.chernakov.rocketscienceapp.presentation.fragment.BaseMenuPageFragment
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.util.lifecycle.SafeObserver

class AppFeaturesFragment : BaseMenuPageFragment() {
    private val appFeaturesViewModel: AppFeaturesViewModel by viewModel()
    private val navigator: AppFeaturesNavigation by inject()

    private val appFeaturesAdapter: AppFeaturesAdapter by lazy {
        AppFeaturesAdapter().apply {
            onClickListener = { navigateToAppFeature(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvFeed.apply {
            adapter = appFeaturesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
        }

        observeData()
    }

    private fun observeData() {
        appFeaturesViewModel.appFeaturesLiveData.observe(viewLifecycleOwner, SafeObserver {
            appFeaturesAdapter.setData(it)
        })
    }

    private fun navigateToAppFeature(appFeature: AppFeature) {
        when (appFeature.id) {
            AppFeature.BUBBLE_GAME_ID -> navigator.openBubbleGame()
            AppFeature.MOVIES_ID -> navigator.openMovies()
            AppFeature.APPMONITOR_ID -> navigator.openAppMonitor()
            AppFeature.PAINT_ID -> navigator.openPaint()
        }
    }

    override fun getLayout(): Int = R.layout.fragment_appfeatures

    override fun obtainViewModel(): BaseViewModel = appFeaturesViewModel
}