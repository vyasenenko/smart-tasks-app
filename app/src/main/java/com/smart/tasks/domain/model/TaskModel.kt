package com.smart.tasks.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDate


@Entity
data class TaskModel(

    @PrimaryKey
    @SerializedName("id")
    val id: String,

    @ColumnInfo(name = "description")
    @SerializedName("Description")
    val description: String,

    @ColumnInfo(name = "due_date")
    @SerializedName("DueDate")
    val dueDate: LocalDate?,

    @ColumnInfo(name = "priority")
    @SerializedName("Priority")
    val priority: Int,

    @ColumnInfo(name = "target_date")
    @SerializedName("TargetDate")
    val targetDate: LocalDate,

    @ColumnInfo(name = "title")
    @SerializedName("Title")
    val title: String,

    @ColumnInfo(name = "status")
    val status: TaskStatus? = TaskStatus.READY_FOR_DEV,

    @ColumnInfo(name = "comment")
    @SerializedName("comment")
    val comment: String? = null,
)
