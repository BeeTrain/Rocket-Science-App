package ru.chernakov.rocketscienceapp.presentation.ui.features.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.data.model.Feature
import ru.chernakov.rocketscienceapp.presentation.ui.features.adapter.holder.FeatureViewHolder

class FeaturesAdapter : RecyclerView.Adapter<FeatureViewHolder>() {
    var items = mutableListOf<Feature>()
    var onClickListener: ((feature: Feature) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_feature, parent, false)
        return FeatureViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.count()

    fun setData(features: List<Feature>) {
        items.clear()
        items.addAll(features)
        notifyDataSetChanged()
    }
}