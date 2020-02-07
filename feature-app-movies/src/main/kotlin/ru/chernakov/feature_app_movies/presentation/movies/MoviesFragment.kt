package ru.chernakov.feature_app_movies.presentation.movies

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_movies.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.core_base.util.lifecycle.SafeObserver
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.feature_app_movies.R
import ru.chernakov.feature_app_movies.presentation.movies.adapter.MoviesAdapter

class MoviesFragment : BaseFragment() {
    private val moviesViewModel: MoviesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val moviesAdapter =
            MoviesAdapter()
        rvMovies.apply {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            adapter = moviesAdapter
            itemAnimator = DefaultItemAnimator()
        }

        moviesViewModel.moviesData.observe(viewLifecycleOwner, SafeObserver {
            moviesAdapter.setData(it)
        })
    }

    override fun getLayout() = R.layout.fragment_movies

    override fun obtainViewModel() = moviesViewModel
}