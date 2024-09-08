package com.smart.tasks.common.recycleradapter

import android.view.View

abstract class ItemViewModel<Item> {

    abstract val item: Item

    private var itemClickHandler: ((view: View, item: Item) -> Unit)? = null

    fun setOnItemClickListener(itemClickHandler: (view: View, item: Item) -> Unit) = this.apply {
        this.itemClickHandler = itemClickHandler
    }

    fun onItemClick(view: View) {
        itemClickHandler?.invoke(view, item)
    }
}