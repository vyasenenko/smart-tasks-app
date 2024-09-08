package com.smart.tasks.common.recycleradapter

import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView


@BindingAdapter(
    "items", "lifecycleOwner", requireAll = false
)
fun RecyclerView.setRecyclerViewItems(
    items: List<RecyclerItem>?,
    lifecycleOwner: LifecycleOwner?,
) {
    var adapter = (this.adapter as? DataBindingRecyclerAdapter)
    if (adapter == null) {
        adapter = DataBindingRecyclerAdapter()
        this.adapter = adapter
    }

    adapter.lifecycleOwner = lifecycleOwner
    adapter.submitList(items.orEmpty())
}

internal fun RecyclerItem.bind(binding: ViewDataBinding) {
    val isVariableFound = binding.setVariable(variableId, data)
    if (isVariableFound.not()) {
        throw IllegalStateException(
            buildErrorMessage(variableId, binding)
        )
    }
}

private fun buildErrorMessage(
    variableId: Int, binding: ViewDataBinding
): String {
    val variableName = DataBindingUtil.convertBrIdToString(variableId)
    val className = binding::class.simpleName
    return "Failed to find variable='$variableName' in the following databinding layout: $className"
}
