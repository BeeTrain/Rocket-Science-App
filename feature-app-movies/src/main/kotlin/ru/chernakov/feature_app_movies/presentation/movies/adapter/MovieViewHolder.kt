package ru.chernakov.feature_app_movies.presentation.movies.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_movie.view.*
import ru.chernakov.feature_app_movies.data.model.Movie

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    lateinit var item: Movie

    fun bind(movie: Movie) {
        item = movie
        itemView.apply {
            Picasso.get()
                .load(item.getPosterLoadingUrl())
                .into(ivPoster)
        }
    }
}