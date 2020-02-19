package ru.chernakov.feature_app_appmonitor.presentation.list.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_app.view.*
import ru.chernakov.feature_app_appmonitor.data.model.ApplicationItem

class AppsListViewHolder(
    itemView: View,
    onItemClickListener: ((ApplicationItem) -> Unit)?
) : RecyclerView.ViewHolder(itemView) {
    lateinit var item: ApplicationItem

    init {
        itemView.setOnClickListener { onItemClickListener?.invoke(item) }
    }

    fun bind(app: ApplicationItem) {
        item = app
        itemView.apply {
            tvAppName.text = item.name
            tvAppApk.text = item.appPackage
            ivAppIcon.setImageDrawable(item.icon)
        }
    }
}