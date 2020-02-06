package ru.chernakov.feature_app_movies.presentation.movies

import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.feature_app_movies.R

class MoviesFragment : BaseFragment() {
    private val moviesViewModel: MoviesViewModel by viewModel()

    override fun getLayout() = R.layout.fragment_movies

    override fun obtainViewModel() = moviesViewModel
}