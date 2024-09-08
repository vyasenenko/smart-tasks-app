package com.smart.tasks.domain.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.smart.tasks.common.converter.LocalDateConverter
import com.smart.tasks.common.converter.TaskStatusConverter
import com.smart.tasks.domain.local.dao.TaskDao
import com.smart.tasks.domain.model.TaskModel

@Database(entities = [TaskModel::class], version = 1)
@TypeConverters(LocalDateConverter::class, TaskStatusConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}