package ru.chernakov.core_ui.extension.android.widget

import android.widget.CompoundButton

fun CompoundButton.updateCheckedWithoutNotify(
    isCheckedValue: Boolean,
    listener: CompoundButton.OnCheckedChangeListener
) {
    setOnCheckedChangeListener(null)
    isChecked = isCheckedValue
    setOnCheckedChangeListener(listener)
}