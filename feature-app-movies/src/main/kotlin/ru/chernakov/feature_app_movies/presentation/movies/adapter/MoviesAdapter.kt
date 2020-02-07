package ru.chernakov.feature_app_movies.presentation.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.chernakov.feature_app_movies.R
import ru.chernakov.feature_app_movies.data.model.Movie

class MoviesAdapter : RecyclerView.Adapter<MovieViewHolder>() {
    var items = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun setData(movies: List<Movie>) {
        items.clear()
        items.addAll(movies)
        notifyDataSetChanged()
    }
}