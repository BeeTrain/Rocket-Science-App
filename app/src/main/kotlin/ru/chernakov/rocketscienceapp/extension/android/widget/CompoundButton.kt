package ru.chernakov.rocketscienceapp.extension.android.widget

import android.widget.CompoundButton

fun CompoundButton.updateCheckedWithoutNotify(
    isCheckedValue: Boolean,
    listener: CompoundButton.OnCheckedChangeListener
) {
    setOnCheckedChangeListener(null)
    isChecked = isCheckedValue
    setOnCheckedChangeListener(listener)
}