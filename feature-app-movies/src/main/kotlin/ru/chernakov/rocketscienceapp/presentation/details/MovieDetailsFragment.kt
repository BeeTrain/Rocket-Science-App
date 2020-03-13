package ru.chernakov.rocketscienceapp.presentation.details

import android.os.Bundle
import android.view.View
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionListenerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_movie_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.chernakov.rocketscienceapp.data.model.Movie
import ru.chernakov.rocketscienceapp.extension.android.view.visibleOrGone
import ru.chernakov.rocketscienceapp.extension.android.view.visibleOrInvisible
import ru.chernakov.rocketscienceapp.movies.R
import ru.chernakov.rocketscienceapp.presentation.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.util.glide.TransitionRequestListener
import ru.chernakov.rocketscienceapp.util.lifecycle.SafeObserver

class MovieDetailsFragment : BaseFragment() {
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModel {
        parametersOf(requireArguments().getString(KEY_MOVIE_JSON))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)
            .addListener(object : TransitionListenerAdapter() {
                override fun onTransitionEnd(transition: Transition) {
                    super.onTransitionEnd(transition)
                    content.visibleOrInvisible(true)
                }
            })
        postponeEnterTransition()
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
        movie.run {
            tvTitle.text = title
            tvVote.text = voteAverage
            tvOverview.text = overview
            tvReleaseDate.text = releaseDate
            ivPoster.transitionName = title

            Glide.with(requireContext())
                .load(movie.getPosterLoadingUrl())
                .error(R.drawable.img_movie_details_stub)
                .apply(RequestOptions().dontTransform())
                .listener(TransitionRequestListener(this@MovieDetailsFragment))
                .into(ivPoster)
        }
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