package ru.chernakov.rocketscienceapp.util.lifecycle

import androidx.lifecycle.Observer

open class SafeObserver<T>(val action: (T) -> Unit) : Observer<T> {
    override fun onChanged(t: T?) {
        t?.let { action(it) }
    }
}