package ru.chernakov.feature_app_movies.presentation.details

import android.os.Bundle
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.chernakov.core_base.util.lifecycle.SafeObserver
import ru.chernakov.core_ui.extension.android.view.visibleOrGone
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.feature_app_movies.R

class MovieDetailsFragment : BaseFragment() {
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModel {
        parametersOf(requireArguments().getString(KEY_MOVIE_JSON))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieDetailsViewModel.loading.observe(viewLifecycleOwner, SafeObserver {
            pbLoading.visibleOrGone(it)
        })
        movieDetailsViewModel.movieData.observe(viewLifecycleOwner, SafeObserver {
            tvTitle.text = it.title
            Picasso.get()
                .load(it.getPosterLoadingUrl())
                .into(ivPoster)
        })
    }

    override fun getLayout() = R.layout.fragment_movie_details

    override fun obtainViewModel() = movieDetailsViewModel

    companion object {
        private const val KEY_MOVIE_JSON = "MOVIE_JSON"
    }
}