package ru.chernakov.rocketscienceapp.presentation.info.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_option.view.*
import ru.chernakov.rocketscienceapp.data.model.OptionItem

class OptionViewHolder(
    itemView: View,
    onItemClickListener: ((OptionItem) -> Unit)?
) : RecyclerView.ViewHolder(itemView) {
    lateinit var item: OptionItem

    init {
        itemView.setOnClickListener { onItemClickListener?.invoke(item) }
    }

    fun bind(option: OptionItem) {
        item = option
        itemView.apply {
            tvTitle.text = context.getString(item.titleRes)
            ivIcon.setImageResource(item.iconRes)
        }
    }
}