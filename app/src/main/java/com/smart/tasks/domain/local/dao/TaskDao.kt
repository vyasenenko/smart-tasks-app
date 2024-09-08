package com.smart.tasks.domain.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.smart.tasks.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM taskmodel")
    fun getAll(): Flow<List<TaskModel>>

    @Query("SELECT * FROM taskmodel WHERE id = :id")
    fun get(id: String): Flow<TaskModel?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg task: TaskModel)

    @Update
    suspend fun update(taskModel: TaskModel)
}