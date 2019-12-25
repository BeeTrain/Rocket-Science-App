package ru.chernakov.core_ui.extension.androidx.appcompat.app

import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ru.chernakov.core_ui.R

fun AppCompatActivity.setupToolbar() {
    val toolbar = findViewById<View>(R.id.toolbar) as? Toolbar
    setSupportActionBar(toolbar)
}

fun AppCompatActivity.setupToolbar(title: String) {
    val toolbar = findViewById<View>(R.id.toolbar) as? Toolbar
    toolbar?.let {
        it.title = title
        setSupportActionBar(toolbar)
    }
}

fun AppCompatActivity.setToolbarTitle(@StringRes title: Int) = supportActionBar?.setTitle(title)

fun AppCompatActivity.setToolbarTitle(title: CharSequence) {
    supportActionBar?.title = title
}

fun AppCompatActivity.setToolbarSubtitle(@StringRes subtitle: Int) {
    val toolbar = findViewById<View>(R.id.toolbar) as? Toolbar
    // hack to render toolbar after subtitle is set if called in onActivityCreated
    toolbar?.post { supportActionBar?.setSubtitle(subtitle) }
}

fun AppCompatActivity.setToolbarSubtitle(subtitle: String) {
    val toolbar = findViewById<View>(R.id.toolbar) as? Toolbar
    // hack to render toolbar after subtitle is set if called in onActivityCreated
    toolbar?.post { supportActionBar?.subtitle = subtitle }
}

fun AppCompatActivity.setHomeEnabled() = supportActionBar?.setDisplayHomeAsUpEnabled(true)

fun AppCompatActivity.setHomeDisabled() = supportActionBar?.setDisplayHomeAsUpEnabled(false)