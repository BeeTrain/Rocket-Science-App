package ru.chernakov.rocketscienceapp.presentation.info.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.data.model.OptionItem
import ru.chernakov.rocketscienceapp.presentation.info.adapter.holder.OptionViewHolder

class OptionsAdapter : RecyclerView.Adapter<OptionViewHolder>() {
    var items = mutableListOf<OptionItem>()
    var onItemClickListener: ((OptionItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_option, parent, false)
        return OptionViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun setData(features: List<OptionItem>) {
        items.clear()
        items.addAll(features)
        notifyDataSetChanged()
    }
}