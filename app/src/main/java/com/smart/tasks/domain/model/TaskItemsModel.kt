package com.smart.tasks.domain.model

import com.google.gson.annotations.SerializedName


data class TaskItemsModel(
    @SerializedName("tasks")
    val tasks: List<TaskModel>
)
