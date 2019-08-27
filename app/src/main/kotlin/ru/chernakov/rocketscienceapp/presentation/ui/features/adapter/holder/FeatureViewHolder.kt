package ru.chernakov.rocketscienceapp.presentation.ui.features.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_feature.view.*
import ru.chernakov.rocketscienceapp.data.model.Feature

class FeatureViewHolder(
    itemView: View,
    onClickListener: ((feature: Feature) -> Unit)?
) : RecyclerView.ViewHolder(itemView) {

    private lateinit var item: Feature

    init {
        itemView.setOnClickListener { onClickListener?.invoke(item) }
    }

    fun bind(feature: Feature) {
        item = feature
        itemView.apply {
            tvTitle.text = item.name
        }
    }
}