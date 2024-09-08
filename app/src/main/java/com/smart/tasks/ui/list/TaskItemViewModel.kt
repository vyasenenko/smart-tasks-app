package com.smart.tasks.ui.list

import android.content.Context
import com.smart.tasks.BR
import com.smart.tasks.R
import com.smart.tasks.common.converter.LocalDateConverter.Companion.DISPLAY_FORMATTER
import com.smart.tasks.common.recycleradapter.ItemViewModel
import com.smart.tasks.common.recycleradapter.RecyclerItem
import com.smart.tasks.common.recycleradapter.comparator.RecyclerItemComparator
import com.smart.tasks.domain.model.TaskModel
import com.smart.tasks.domain.model.TaskStatus
import org.threeten.bp.Duration

class TaskItemViewModel(override val item: TaskModel) :
    ItemViewModel<TaskModel>(), RecyclerItemComparator {

    fun taskTitle() =
        item.title

    fun dueDays(context: Context) =
        item.dueDate?.let { DISPLAY_FORMATTER.format(it) } ?: context.getString(R.string.n_a)

    fun daysLeft(context: Context) =
        item.dueDate?.let {
            Duration.between(
                item.targetDate.atTime(0, 0),
                it.atTime(0, 0)
            ).toDays().toString()
        } ?: context.getString(R.string.n_a)

    fun taskColor(context: Context) =
        when (item.status) {
            TaskStatus.RESOLVED -> {
                context.getColor(R.color.text_title_resolved)
            }

            else -> {
                context.getColor(R.color.text_title_unresolved)
            }
        }

    fun statusImage() =
        when (item.status) {
            TaskStatus.RESOLVED -> R.drawable.btn_resolved
            TaskStatus.UNRESOLVED -> R.drawable.btn_unresolved
            else -> null
        }

    fun isCompleted() =
        (item.status != null && item.status != TaskStatus.READY_FOR_DEV)


    override fun isSameItem(other: Any): Boolean {
        if (other is TaskItemViewModel) {
            return other.item.id == item.id
        }
        return false
    }

    override fun isSameContent(other: Any): Boolean {
        if (other is TaskItemViewModel) {
            return other.item == item
        }
        return false
    }

    fun toRecycleItem() =
        RecyclerItem(this, R.layout.item_task, BR.viewModel)
}