package com.smart.tasks.common.recycleradapter.comparator


interface RecyclerItemComparator {

    /**
     * Is same item by id.
     */
    fun isSameItem(other: Any): Boolean {
        return false
    }

    /**
     * Is same content in model.
     */
    fun isSameContent(other: Any): Boolean {
        return false
    }
}