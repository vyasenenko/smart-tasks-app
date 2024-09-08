package com.smart.tasks.common.converter

import androidx.room.TypeConverter
import com.smart.tasks.domain.model.TaskStatus

class TaskStatusConverter {

    @TypeConverter
    fun storedStringToTaskStatus(value: String?): TaskStatus? {
        return value?.let {
            TaskStatus.valueOf(it)
        }
    }

    @TypeConverter
    fun taskStatusToStoredString(status: TaskStatus?): String? {
        return status?.name
    }
}