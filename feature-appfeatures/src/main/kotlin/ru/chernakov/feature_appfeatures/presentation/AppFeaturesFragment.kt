package ru.chernakov.feature_appfeatures.presentation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_appfeatures.*
import org.koin.android.ext.android.inject
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel
import ru.chernakov.feature_appfeatures.R
import ru.chernakov.feature_appfeatures.data.model.AppFeature
import ru.chernakov.feature_appfeatures.navigation.AppFeaturesNavigation
import ru.chernakov.feature_appfeatures.presentation.adapter.AppFeaturesAdapter

class AppFeaturesFragment : BaseFragment() {
    private val navigator: AppFeaturesNavigation by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val feedAdapter = AppFeaturesAdapter().apply {
            onClickListener = {
                navigateToAppFeature(it)
            }
        }
        rvFeed.apply {
            adapter = feedAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
        }
        val features =
            listOf(
                AppFeature(AppFeature.BUBBLE_GAME_ID, "Bubble game", R.drawable.ic_bubble_game),
                AppFeature(1, "qwe"),
                AppFeature(2, "asd"),
                AppFeature(3, "zxc")
            )
        feedAdapter.setData(features)
    }

    private fun navigateToAppFeature(appFeature: AppFeature) {
        when (appFeature.id) {
            AppFeature.BUBBLE_GAME_ID -> navigator.openBubbleGame()
        }
    }

    override fun getLayout(): Int = R.layout.fragment_appfeatures

    override fun obtainViewModel(): BaseViewModel = BaseViewModel()

    companion object {

        fun newInstance() = AppFeaturesFragment()
    }
}