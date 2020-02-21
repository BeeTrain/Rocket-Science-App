package ru.chernakov.rocketscienceapp.presentation

import org.koin.android.ext.android.inject
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.navigaton.FavoriteNavigation
import ru.chernakov.rocketscienceapp.presentation.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel

class FavoriteFragment : BaseFragment() {
    private val navigator: FavoriteNavigation by inject()

    override fun getLayout() = R.layout.fragment_favorite
    override fun obtainViewModel() = BaseViewModel()
}