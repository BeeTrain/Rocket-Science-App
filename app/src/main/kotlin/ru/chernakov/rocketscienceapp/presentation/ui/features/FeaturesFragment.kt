package ru.chernakov.rocketscienceapp.presentation.ui.features

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_feed.*
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.data.model.Feature
import ru.chernakov.rocketscienceapp.presentation.ui.base.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.presentation.ui.base.viewmodel.BaseViewModel
import ru.chernakov.rocketscienceapp.presentation.ui.features.adapter.FeaturesAdapter

class FeaturesFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val feedAdapter = FeaturesAdapter()
        rvFeed.apply {
            adapter = feedAdapter
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
        }
        val features = listOf(Feature(0,"qwe"), Feature(0,"qwe"), Feature(0,"qwe"), Feature(0,"qwe"))
        feedAdapter.setData(features)
    }

    override fun getLayout(): Int = R.layout.fragment_feed

    override fun obtainViewModel(): BaseViewModel = BaseViewModel()

    companion object {

        fun newInstance() = FeaturesFragment()
    }
}