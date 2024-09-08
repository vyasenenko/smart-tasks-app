package com.smart.tasks.ui.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.smart.tasks.R
import com.smart.tasks.common.CommonViewModel
import com.smart.tasks.common.SingleLiveEvent
import com.smart.tasks.common.converter.LocalDateConverter.Companion.DISPLAY_FORMATTER
import com.smart.tasks.domain.model.TaskModel
import com.smart.tasks.domain.model.TaskStatus
import com.smart.tasks.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.threeten.bp.Duration
import javax.inject.Inject


@HiltViewModel
class TaskDetailsViewModel @Inject constructor() : CommonViewModel() {

    @Inject
    lateinit var repository: MainRepository

    private val mDetails: MutableLiveData<TaskModel> = MutableLiveData()
    private val mRequestChangeStatus: SingleLiveEvent<TaskStatus> = SingleLiveEvent()

    val comment: MutableLiveData<String?> = MutableLiveData()

    val requestChangeStatus: LiveData<TaskStatus>
        get() = mRequestChangeStatus

    val details: LiveData<TaskModel>
        get() = mDetails

    fun taskTitle() = mDetails.map {
        it.title
    }

    fun dueDays(context: Context) = mDetails.map { details ->
        details.dueDate?.let { DISPLAY_FORMATTER.format(it) } ?: context.getString(R.string.n_a)
    }

    fun daysLeft(context: Context) = mDetails.map { model ->
        model.dueDate?.let {
            Duration.between(
                model.targetDate.atTime(0, 0),
                it.atTime(0, 0)
            ).toDays().toString()
        } ?: context.getString(R.string.n_a)
    }

    fun description() = mDetails.map {
        it.title
    }

    fun statusName(context: Context) = mDetails.map {
        if (it.status == TaskStatus.RESOLVED) {
            context.getString(R.string.resolved)
        } else {
            context.getString(R.string.unresolved)
        }
    }

    fun statusImage() = mDetails.map {
        if (it.status == TaskStatus.RESOLVED) {
            R.drawable.sign_resolved
        } else {
            R.drawable.unresolved_sign
        }
    }

    fun isTaskCompleted() = mDetails.map {
        (it.status != null && it.status != TaskStatus.READY_FOR_DEV)
    }

    fun taskColor(context: Context) = mDetails.map {
        when (it?.status) {
            TaskStatus.RESOLVED -> {
                context.getColor(R.color.text_title_resolved)
            }

            else -> {
                context.getColor(R.color.text_title_unresolved)
            }
        }
    }

    fun statusColor(context: Context) = mDetails.map {
        when (it?.status) {
            TaskStatus.RESOLVED -> {
                context.getColor(R.color.text_title_resolved)
            }

            TaskStatus.UNRESOLVED -> {
                context.getColor(R.color.text_title_unresolved)
            }

            else -> {
                context.getColor(R.color.text_title_non_resolved)
            }
        }
    }

    fun requestChangeStatus(status: TaskStatus) {
        mRequestChangeStatus.postValue(status)
    }

    fun changeStatus(status: TaskStatus) = viewModelScope.launch {
        repository.updateTask(mDetails.value!!.copy(status = status, comment = comment.value))
    }


    fun showTask(id: String) = viewModelScope.launch {
        repository.getTaskDetails(id).collect {
            comment.postValue(it?.comment)
            mDetails.postValue(it)
        }
    }
}