package ru.chernakov.rocketscienceapp.presentation.movies.adapter

import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.list_item_movie.view.*
import ru.chernakov.rocketscienceapp.data.model.Movie
import ru.chernakov.rocketscienceapp.movies.R

class MovieViewHolder(
    itemView: View,
    onItemClickListener: ((View, Movie) -> Unit)?
) : RecyclerView.ViewHolder(itemView) {

    lateinit var item: Movie

    init {
        itemView.setOnClickListener { onItemClickListener?.invoke(itemView.ivPoster, item) }
    }

    fun bind(movie: Movie) {
        item = movie
        itemView.apply {
            Glide.with(context)
                .load(item.getPosterLoadingUrl())
                .error(R.drawable.ic_movie_stub)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivPoster)
            ViewCompat.setTransitionName(ivPoster, item.title)
        }
    }
}