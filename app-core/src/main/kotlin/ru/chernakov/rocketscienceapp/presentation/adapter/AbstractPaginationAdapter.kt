package ru.chernakov.rocketscienceapp.presentation.adapter

import androidx.annotation.CallSuper
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractPaginationAdapter<T : Any, H : RecyclerView.ViewHolder> @JvmOverloads constructor(
    loadOffset: Int = LOAD_OFFSET,

    diffCallback: DiffUtil.ItemCallback<T> = EqualsDiffItemCallback<T>()
) : ListAdapter<T, H>(diffCallback) {

    var callback: Callback? = null
    protected var loadOffset = LOAD_OFFSET
    protected var paginationEnabled = true

    init {
        this.loadOffset = loadOffset
    }

    constructor(callback: Callback) : this(LOAD_OFFSET) {
        this.callback = callback
    }

    constructor(loadOffset: Int, callback: Callback) : this(loadOffset) {
        this.callback = callback
    }

    @CallSuper
    override fun onBindViewHolder(holder: H, position: Int) {
        if (paginationEnabled && position == itemCount - loadOffset) {
            callback?.loadMore()
        }
    }

    interface Callback {
        fun loadMore()
    }

    companion object {
        const val LOAD_OFFSET = 6
    }
}