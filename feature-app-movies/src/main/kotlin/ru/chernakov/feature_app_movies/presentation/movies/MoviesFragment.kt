package ru.chernakov.feature_app_movies.presentation.movies

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_movies.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.core_base.util.lifecycle.SafeObserver
import ru.chernakov.core_ui.extension.android.view.visibleOrGone
import ru.chernakov.core_ui.presentation.adapter.AbstractPaginationAdapter
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.feature_app_movies.R
import ru.chernakov.feature_app_movies.navigation.MoviesNavigation
import ru.chernakov.feature_app_movies.presentation.movies.adapter.MoviesAdapter

class MoviesFragment : BaseFragment(), AbstractPaginationAdapter.Callback {
    private val moviesViewModel: MoviesViewModel by viewModel()
    private val navigator: MoviesNavigation by inject()

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() = navigator.fromMoviesToAppFeatures()
            }
        )
        moviesViewModel.loading.observe(viewLifecycleOwner, SafeObserver {
            pbLoading.visibleOrGone(it)
        })

        initList()
    }

    private fun initList() {
        moviesAdapter = MoviesAdapter(LOAD_OFFSET).apply {
            callback = this@MoviesFragment
        }
        rvMovies.apply {
            layoutManager =
                StaggeredGridLayoutManager(ROW_SIZE, StaggeredGridLayoutManager.VERTICAL)
            adapter = moviesAdapter
            itemAnimator = DefaultItemAnimator()
        }
        moviesViewModel.moviesData.observe(viewLifecycleOwner, SafeObserver {
            moviesAdapter.setData(it.first, it.second == 1)
        })
    }

    override fun getLayout() = R.layout.fragment_movies

    override fun obtainViewModel() = moviesViewModel

    override fun loadMore() {
        moviesViewModel.loadMore()
    }

    companion object {
        private const val ROW_SIZE = 3
        private const val LOAD_OFFSET = 1
    }
}