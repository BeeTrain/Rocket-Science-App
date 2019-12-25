package ru.chernakov.core_ui.extension.android.widget

import android.text.TextWatcher
import android.widget.TextView

inline fun TextView.addTextChangedListener(init: KTextWatcher.() -> Unit): TextWatcher {
    return KTextWatcher().apply {
        init()
        addTextChangedListener(this)
    }
}