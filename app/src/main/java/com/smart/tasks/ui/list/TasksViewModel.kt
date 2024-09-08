package com.smart.tasks.ui.list

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.smart.tasks.R
import com.smart.tasks.common.CommonViewModel
import com.smart.tasks.common.SingleLiveEvent
import com.smart.tasks.common.converter.LocalDateConverter.Companion.DISPLAY_FORMATTER
import com.smart.tasks.common.flow.State
import com.smart.tasks.common.recycleradapter.RecyclerItem
import com.smart.tasks.common.zipNullable
import com.smart.tasks.domain.model.TaskItemsModel
import com.smart.tasks.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import javax.inject.Inject


@HiltViewModel
class TasksViewModel @Inject constructor() : CommonViewModel() {

    @Inject
    lateinit var repository: MainRepository

    private val mItemClick: SingleLiveEvent<Pair<View, String>> = SingleLiveEvent()

    private val mList: MutableLiveData<TaskItemsModel?> = MutableLiveData()

    private val mDate: MutableLiveData<LocalDate> = MutableLiveData(LocalDate.now())

    val itemClick: LiveData<Pair<View, String>>
        get() = mItemClick

    fun dateName(context: Context): LiveData<String> = mDate.map {
        if (it.isEqual(LocalDate.now())) {
            context.getString(R.string.today)
        } else {
            DISPLAY_FORMATTER.format(it)
        }
    }

    val list: LiveData<List<RecyclerItem>>
        get() = mList.zipNullable(mDate).map { (list, date) ->
            list?.tasks?.filter { it.targetDate == date }?.sortedByDescending { it.priority }
                ?.map { task ->
                    TaskItemViewModel(task).apply {
                            setOnItemClickListener { view, item ->
                                mItemClick.postValue(view to item.id)
                            }
                        }.toRecycleItem()
                }.orEmpty()
        }

    fun moveDayForward() {
        mDate.postValue(mDate.value?.plusDays(1))
    }

    fun moveDayBack() {
        mDate.postValue(mDate.value?.minusDays(1))
    }

    fun showTasks(context: Context) = viewModelScope.launch {
        repository.fetchTasks(context).collect {
            when (it) {
                is State.Loading -> {
                    progress.postValue(it.isLoading)
                }

                is State.Success -> {
                    if (!it.data?.tasks.isNullOrEmpty()) {
                        mList.postValue(it.data)
                    }
                }

                else -> {}
            }
        }
    }
}