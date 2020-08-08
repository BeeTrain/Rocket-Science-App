package ru.chernakov.rocketscienceapp.widget.colorpicker

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.w_palette.*
import ru.chernakov.rocketscienceapp.core.R
import ru.chernakov.rocketscienceapp.presentation.fragment.BaseBottomSheetDialog

class ColorPicker : BaseBottomSheetDialog() {
    override val layoutResId = R.layout.w_palette
    private var callback: OnColorSelectListener? = null

    private var colorItems = mutableListOf<ColorPickerItem>()
    var selectedColor: ColorPickerItem? = null

    private val adapter: ColorPickerAdapter by lazy {
        ColorPickerAdapter(colorItems).apply {
            onClickListener = { pickedItem ->
                selectedColor = pickedItem
                adapter.items.forEach {
                    if (it.isChecked && it != selectedColor) {
                        it.isChecked = false
                        adapter.notifyItemChanged(adapter.items.indexOf(it))
                    } else if (it == selectedColor && !it.isChecked) {
                        it.isChecked = true
                        adapter.notifyItemChanged(adapter.items.indexOf(it))
                    }
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        resolveCallback(context)
    }

    private fun resolveCallback(context: Context?) {
        if (parentFragment is OnColorSelectListener) {
            callback = parentFragment as OnColorSelectListener
        } else if (context is OnColorSelectListener) {
            callback = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        prepareColors()
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvPalette.apply {
            layoutManager = GridLayoutManager(context, COLOR_COLUMNS_DEFAULT)
            adapter = this@ColorPicker.adapter
            itemAnimator = DefaultItemAnimator()
        }

        btApply.setOnClickListener {
            callback?.onColorSelected(selectedColor?.color)
            dismissAllowingStateLoss()
        }
        btCancel.setOnClickListener {
            callback?.onCancel()
            dismissAllowingStateLoss()
        }
    }

    private fun prepareColors() {
        requireContext().resources.obtainTypedArray(R.array.color_picker_colors).run {
            for (i in 0 until length()) {
                colorItems.add(
                    ColorPickerItem(
                        getColor(i, 0),
                        false
                    )
                )
            }

            recycle()
        }
    }

    interface OnColorSelectListener {
        fun onColorSelected(color: Int?)
        fun onCancel()
    }

    companion object {
        private const val COLOR_COLUMNS_DEFAULT = 6

        fun show(fm: FragmentManager) {
            val dialog = ColorPicker()
            dialog.show(fm, ColorPicker::class.java.toString())
        }
    }
}