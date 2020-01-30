package ru.chernakov.feature_favorite.presentation

import org.koin.android.ext.android.inject
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.core_ui.presentation.viewmodel.BaseViewModel
import ru.chernakov.feature_favorite.R
import ru.chernakov.feature_favorite.navigaton.FavoriteNavigation

class FavoriteFragment : BaseFragment() {
    private val navigator: FavoriteNavigation by inject()

    override fun getLayout() = R.layout.fragment_favorite
    override fun obtainViewModel() = BaseViewModel()
}