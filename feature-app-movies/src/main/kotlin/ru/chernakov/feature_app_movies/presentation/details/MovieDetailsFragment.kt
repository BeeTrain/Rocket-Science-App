package ru.chernakov.feature_app_movies.presentation.details

import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.feature_app_movies.R

class MovieDetailsFragment : BaseFragment() {
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModel()

    override fun getLayout() = R.layout.fragment_movie_info

    override fun obtainViewModel() = movieDetailsViewModel
}