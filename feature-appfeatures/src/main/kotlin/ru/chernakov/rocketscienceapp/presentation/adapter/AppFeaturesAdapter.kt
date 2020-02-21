package ru.chernakov.rocketscienceapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.data.model.AppFeature
import ru.chernakov.rocketscienceapp.presentation.adapter.holder.AppFeaturesViewHolder

class AppFeaturesAdapter : RecyclerView.Adapter<AppFeaturesViewHolder>() {
    var items = mutableListOf<AppFeature>()
    var onClickListener: ((feature: AppFeature) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppFeaturesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_appfeature, parent, false)
        return AppFeaturesViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: AppFeaturesViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.count()

    fun setData(features: List<AppFeature>) {
        items.clear()
        items.addAll(features)
        notifyDataSetChanged()
    }
}