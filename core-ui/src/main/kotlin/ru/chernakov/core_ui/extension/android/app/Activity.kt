package ru.chernakov.core_ui.extension.android.app

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    if (imm != null && window != null) {
        var currentFocus = window.currentFocus
        if (currentFocus == null) {
            currentFocus = window.decorView.findFocus()
        }
        if (currentFocus != null) {
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
            currentFocus.clearFocus()
        }
    }
}