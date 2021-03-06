package ru.chernakov.rocketscienceapp.presentation.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.chernakov.rocketscienceapp.data.model.Movie
import ru.chernakov.rocketscienceapp.movies.R
import ru.chernakov.rocketscienceapp.presentation.adapter.AbstractPaginationAdapter

class MoviesAdapter(loadOffset: Int) : AbstractPaginationAdapter<Movie, MovieViewHolder>() {
    var items = mutableSetOf<Movie>()
    var onItemClickListener: ((View, Movie) -> Unit)? = null

    init {
        this.loadOffset = loadOffset
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_movie, parent, false)
        return MovieViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.bind(items.toList()[position])
    }

    override fun getItemCount() = items.size

    fun setData(movies: List<Movie>) {
        items.addAll(movies)
        notifyDataSetChanged()
    }
}