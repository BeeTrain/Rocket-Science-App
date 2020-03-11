package ru.chernakov.rocketscienceapp.presentation.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.list_item_appfeature.view.*
import ru.chernakov.rocketscienceapp.data.model.AppFeature

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
            tvTitle.text = context.getString(item.name)
            Glide.with(context)
                .load(item.iconRes)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivFeatureIcon)
        }
    }
}