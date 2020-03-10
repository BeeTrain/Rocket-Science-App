package ru.chernakov.rocketscienceapp.extension.android.view

import android.view.View
import android.view.ViewTreeObserver

fun View?.visibleOrGone(visible: Boolean) {
    this?.visibility = if (visible) View.VISIBLE else View.GONE
}

fun View?.visibleOrInvisible(visible: Boolean) {
    this?.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

fun View?.removeOnGlobalLayoutListener(listener: ViewTreeObserver.OnGlobalLayoutListener) =
        this?.viewTreeObserver?.removeOnGlobalLayoutListener(listener)

fun View.toTransitionGroup() = this to transitionName