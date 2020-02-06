package ru.chernakov.feature_appfeatures.presentation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_appfeatures.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.core_base.util.lifecycle.SafeObserver
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel
import ru.chernakov.feature_appfeatures.R
import ru.chernakov.feature_appfeatures.data.model.AppFeature
import ru.chernakov.feature_appfeatures.navigation.AppFeaturesNavigation
import ru.chernakov.feature_appfeatures.presentation.adapter.AppFeaturesAdapter

class AppFeaturesFragment : BaseFragment() {
    private val appFeaturesViewModel: AppFeaturesViewModel by viewModel()
    private val navigator: AppFeaturesNavigation by inject()

    lateinit var appFeaturesAdapter: AppFeaturesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appFeaturesAdapter = AppFeaturesAdapter().apply {
            onClickListener = {
                navigateToAppFeature(it)
            }
        }
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
        }
    }

    override fun getLayout(): Int = R.layout.fragment_appfeatures

    override fun obtainViewModel(): BaseViewModel = appFeaturesViewModel
}