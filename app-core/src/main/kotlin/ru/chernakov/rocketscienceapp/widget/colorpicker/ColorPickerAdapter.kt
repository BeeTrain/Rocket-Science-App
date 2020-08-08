package ru.chernakov.rocketscienceapp.widget.colorpicker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.chernakov.rocketscienceapp.core.R

class ColorPickerAdapter(
    var items: MutableList<ColorPickerItem> = mutableListOf()
) : RecyclerView.Adapter<ColorPickerViewHolder>() {
    var onClickListener: ((color: ColorPickerItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorPickerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_palette, parent, false)
        return ColorPickerViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: ColorPickerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}