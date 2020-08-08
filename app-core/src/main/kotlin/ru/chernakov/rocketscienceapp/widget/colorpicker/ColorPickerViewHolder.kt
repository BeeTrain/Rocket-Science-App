package ru.chernakov.rocketscienceapp.widget.colorpicker

import android.graphics.PorterDuff
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_palette.view.*
import ru.chernakov.rocketscienceapp.core.R
import ru.chernakov.rocketscienceapp.extension.android.content.getColorKtx

class ColorPickerViewHolder(
    itemView: View,
    onClickListener: ((color: ColorPickerItem) -> Unit)?
) : RecyclerView.ViewHolder(itemView) {

    lateinit var item: ColorPickerItem

    init {
        itemView.setOnClickListener { onClickListener?.invoke(item) }
    }

    fun bind(color: ColorPickerItem) {
        item = color
        itemView.apply {
            vColor.background.setColorFilter(item.color, PorterDuff.Mode.SRC_IN)

            if (item.isChecked) {
                vColorItem.background.setColorFilter(context.getColorKtx(R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
            } else {
                vColorItem.background.setColorFilter(context.getColorKtx(R.color.transparent), PorterDuff.Mode.SRC_IN)
            }
        }
    }
}