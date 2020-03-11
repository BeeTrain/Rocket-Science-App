package ru.chernakov.rocketscienceapp.presentation.movies.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_movie.view.*
import ru.chernakov.rocketscienceapp.data.model.Movie
import ru.chernakov.rocketscienceapp.movies.R

class MovieViewHolder(
    itemView: View,
    onItemClickListener: ((View, Movie) -> Unit)?
) : RecyclerView.ViewHolder(itemView) {

    lateinit var item: Movie

    init {
        itemView.setOnClickListener { onItemClickListener?.invoke(it.ivPoster, item) }
    }

    fun bind(movie: Movie) {
        item = movie
        itemView.apply {
            Picasso.get()
                .load(item.getPosterLoadingUrl())
                .error(R.drawable.ic_movie_stub)
                .into(ivPoster)
        }
    }
}