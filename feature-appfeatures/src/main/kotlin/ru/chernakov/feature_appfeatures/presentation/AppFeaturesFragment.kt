package ru.chernakov.feature_appfeatures.presentation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_appfeatures.*
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel
import ru.chernakov.feature_appfeatures.R
import ru.chernakov.feature_appfeatures.data.model.AppFeature
import ru.chernakov.feature_appfeatures.presentation.adapter.AppFeaturesAdapter

class AppFeaturesFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val feedAdapter = AppFeaturesAdapter()
        rvFeed.apply {
            adapter = feedAdapter
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
        }
        val features =
            listOf(AppFeature(0, "qwe"), AppFeature(0, "qwe"), AppFeature(0, "qwe"), AppFeature(0, "qwe"))
        feedAdapter.setData(features)
    }

    override fun getLayout(): Int = R.layout.fragment_appfeatures

    override fun obtainViewModel(): BaseViewModel = BaseViewModel()

    companion object {

        fun newInstance() = AppFeaturesFragment()
    }
}