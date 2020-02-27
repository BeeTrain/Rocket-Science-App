package ru.chernakov.rocketscienceapp.presentation

import org.koin.android.ext.android.inject
import ru.chernakov.rocketscienceapp.favorite.R
import ru.chernakov.rocketscienceapp.navigaton.FavoriteNavigation
import ru.chernakov.rocketscienceapp.presentation.fragment.BaseMenuPageFragment
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel

class FavoriteFragment : BaseMenuPageFragment() {
    private val navigator: FavoriteNavigation by inject()

    override fun getLayout() = R.layout.fragment_favorite
    override fun obtainViewModel() = BaseViewModel()
}