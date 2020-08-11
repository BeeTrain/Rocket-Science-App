package ru.chernakov.rocketscienceapp.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.w_appbar_search.view.*
import ru.chernakov.rocketscienceapp.core.R
import ru.chernakov.rocketscienceapp.extension.android.content.getColorKtx
import ru.chernakov.rocketscienceapp.extension.android.content.hideKeyboard
import ru.chernakov.rocketscienceapp.extension.android.content.showKeyboard
import ru.chernakov.rocketscienceapp.extension.android.widget.addTextChangedListener

class AppBarSearchLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    var isSearchMode = false
        set(value) {
            field = value
            if (value) {
                prepareSearch()
            } else {
                closeSearch()
            }
        }

    private var callback: OnSearchListener? = null

    private var toolbarColor = context.getColorKtx(R.color.colorPrimary)
        set(value) {
            field = value
            toolbar.setBackgroundColor(value)
        }

    private var title: String = context.getString(R.string.app_name)
        set(value) {
            field = value
            tvTitle.text = value
        }

    private var query: String = ""

    init {
        View.inflate(context, R.layout.w_appbar_search, this)

        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.AppBarSearchLayout, 0, 0)
        try {
            title = attributes.getString(R.styleable.AppBarSearchLayout_toolbarTitle) ?: title
            toolbarColor = attributes.getColor(R.styleable.AppBarSearchLayout_toolbarColor, toolbarColor)
        } finally {
            attributes.recycle()
        }

        ivSearch.setOnClickListener { isSearchMode = true }
        ivClose.setOnClickListener { isSearchMode = false }
        etSearch.addTextChangedListener {
            afterTextChanged {
                query = it.toString()
                callback?.onSearchQueryChanged(query)
            }
        }
    }

    private fun prepareSearch() {
        tvTitle.visibility = View.GONE
        ivSearch.visibility = View.GONE
        ivClose.visibility = View.VISIBLE
        containerSearch.background = context.getDrawable(R.drawable.bg_search)
        etSearch.apply {
            visibility = View.VISIBLE
            setHint(R.string.hint_search)
            isEnabled = true
            isFocusable = true
            isFocusableInTouchMode = true
            requestFocus()
            (context as Activity).showKeyboard(this)
        }
    }

    private fun closeSearch() {
        ivClose.visibility = View.GONE
        tvTitle.visibility = View.VISIBLE
        ivSearch.visibility = View.VISIBLE
        containerSearch.background = null
        etSearch.apply {
            visibility = View.INVISIBLE
            hint = ""
            setText("")
            context.hideKeyboard(this)
            isEnabled = false
            isFocusable = false
            isFocusableInTouchMode = false
            clearFocus()
        }
    }

    fun onResume(listener: OnSearchListener) {
        callback = listener
        if (query.isNotEmpty()) {
            prepareSearch()
            listener.onSearchQueryChanged(query)
        }
    }

    fun onPause() {
        callback = null
    }

    interface OnSearchListener {
        fun onSearchQueryChanged(query: String)
    }
}