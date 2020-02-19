package ru.chernakov.feature_app_appmonitor.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.chernakov.feature_app_appmonitor.R
import ru.chernakov.feature_app_appmonitor.data.model.ApplicationItem
import ru.chernakov.feature_app_appmonitor.presentation.list.adapter.holder.AppsListViewHolder

class AppsListAdapter : RecyclerView.Adapter<AppsListViewHolder>() {
    var items = mutableListOf<ApplicationItem>()
    var onItemClickListener: ((ApplicationItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppsListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_app, parent, false)
        return AppsListViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: AppsListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun setData(features: List<ApplicationItem>) {
        items.clear()
        items.addAll(features)
        notifyDataSetChanged()
    }
}