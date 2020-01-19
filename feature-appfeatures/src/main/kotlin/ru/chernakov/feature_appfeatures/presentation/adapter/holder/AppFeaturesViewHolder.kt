package ru.chernakov.feature_appfeatures.presentation.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_appfeature.view.*
import ru.chernakov.feature_appfeatures.data.model.AppFeature

class AppFeaturesViewHolder(
    itemView: View,
    onClickListener: ((feature: AppFeature) -> Unit)?
) : RecyclerView.ViewHolder(itemView) {

    private lateinit var item: AppFeature

    init {
        itemView.setOnClickListener { onClickListener?.invoke(item) }
    }

    fun bind(feature: AppFeature) {
        item = feature
        itemView.apply {
            tvTitle.text = item.name
            Picasso.get().load(item.iconRes).into(ivFeatureIcon)
        }
    }
}