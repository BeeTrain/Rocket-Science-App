package ru.chernakov.feature_app_movies.presentation.details

import android.os.Bundle
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.chernakov.feature_app_movies.R
import ru.chernakov.feature_app_movies.data.model.Movie
import ru.chernakov.rocketscienceapp.extension.android.view.visibleOrGone
import ru.chernakov.rocketscienceapp.presentation.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.util.lifecycle.SafeObserver

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
            setData(it)
        })
    }

    private fun setData(movie: Movie) {
        tvTitle.text = movie.title
        tvVote.text = movie.voteAverage
        tvOverview.text = movie.overview
        tvReleaseDate.text = movie.releaseDate
        Picasso.get()
            .load(movie.getPosterLoadingUrl())
            .error(R.drawable.img_movie_details_stub)
            .into(ivPoster)
    }

    override fun getLayout() = R.layout.fragment_movie_details

    override fun obtainViewModel() = movieDetailsViewModel

    companion object {
        private const val KEY_MOVIE_JSON = "MOVIE_JSON"

        fun createArgs(movieJson: String): Bundle {
            return Bundle().apply {
                putString(KEY_MOVIE_JSON, movieJson)
            }
        }
    }
}