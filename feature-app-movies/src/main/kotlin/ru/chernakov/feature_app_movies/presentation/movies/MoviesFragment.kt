package ru.chernakov.feature_app_movies.presentation.movies

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movies.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.chernakov.feature_app_movies.R
import ru.chernakov.feature_app_movies.navigation.MoviesNavigation
import ru.chernakov.feature_app_movies.presentation.movies.adapter.MoviesAdapter
import ru.chernakov.rocketscienceapp.extension.android.view.visibleOrGone
import ru.chernakov.rocketscienceapp.presentation.adapter.AbstractPaginationAdapter
import ru.chernakov.rocketscienceapp.presentation.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.util.data.GsonSerialization
import ru.chernakov.rocketscienceapp.util.lifecycle.SafeObserver

class MoviesFragment : BaseFragment(), AbstractPaginationAdapter.Callback {
    private val moviesViewModel: MoviesViewModel by viewModel()
    private val navigator: MoviesNavigation by inject()

    private val moviesAdapter by lazy { MoviesAdapter(LOAD_OFFSET) }
    private val connectionSnackbar by lazy { createNetworkErrorSnackbar() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigator.fromMoviesToAppFeatures()
                    setConnectionSnackbarVisible(false)
                }
            }
        )
        moviesViewModel.loading.observe(viewLifecycleOwner, SafeObserver {
            pbLoading.visibleOrGone(it)
        })
        moviesViewModel.networkErrorEvent.observe(viewLifecycleOwner, SafeObserver {
            setConnectionSnackbarVisible(it)
        })

        initList()
    }

    private fun initList() {
        moviesAdapter.apply {
            callback = this@MoviesFragment
            onItemClickListener = {
                moviesViewModel.selectMovie(it)
                setConnectionSnackbarVisible(false)
            }
        }
        rvMovies.apply {
            layoutManager = StaggeredGridLayoutManager(ROW_SIZE, StaggeredGridLayoutManager.VERTICAL)
            adapter = moviesAdapter
            itemAnimator = DefaultItemAnimator()
        }
        moviesViewModel.moviesData.observe(viewLifecycleOwner, SafeObserver {
            moviesAdapter.setData(it.toList())
        })
        moviesViewModel.selectedMovieEvent.observe(viewLifecycleOwner, SafeObserver {
            navigator.fromMoviesToDetails(GsonSerialization.gson.toJson(it))
        })
    }

    private fun setConnectionSnackbarVisible(isVisible: Boolean) {
        if (isVisible) {
            connectionSnackbar.show()
        } else {
            connectionSnackbar.dismiss()
        }
    }

    private fun createNetworkErrorSnackbar(): Snackbar {
        return Snackbar.make(requireView(), getString(R.string.msg_error_network), Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.button_retry) { loadMore() }
    }

    override fun loadMore() {
        moviesViewModel.loadMore()
    }

    override fun getLayout() = R.layout.fragment_movies

    override fun obtainViewModel() = moviesViewModel

    companion object {
        private const val ROW_SIZE = 3
        private const val LOAD_OFFSET = 1
    }
}