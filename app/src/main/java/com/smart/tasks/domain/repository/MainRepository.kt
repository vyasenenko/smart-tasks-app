package com.smart.tasks.domain.repository

import android.content.Context
import com.google.gson.GsonBuilder
import com.smart.tasks.R
import com.smart.tasks.common.converter.LocalDateConverter
import com.smart.tasks.common.flow.State
import com.smart.tasks.common.flow.safeApiCall
import com.smart.tasks.domain.api.MainApi
import com.smart.tasks.domain.local.dao.TaskDao
import com.smart.tasks.domain.model.TaskItemsModel
import com.smart.tasks.domain.model.TaskModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val mainApi: MainApi, private val dao: TaskDao
) {

    /**
     * The error "CLEARTEXT communication to demo1414406.mockable.io
     * not permitted by network security policy"
     * occurs because Android blocks insecure (HTTP) connections by default,
     * starting with Android 9 (API 28).
     * To allow an insecure connection to a specific server, you need to change the network security policy.
     */
    suspend fun getTasks(): Flow<State<TaskItemsModel>> {
        val cached = dao.getAll().map {
            State.Success(TaskItemsModel(it))
        }

        val apiCall = safeApiCall { mainApi.getTasks() }

        return merge(cached, apiCall)
    }

    suspend fun fetchTasks(
        context: Context,
    ): Flow<State<TaskItemsModel>> = withContext(Dispatchers.IO) {
        val cached = dao.getAll().map {
            State.Success(TaskItemsModel(it))
        }

        val network = context.resources.openRawResource(R.raw.mock_data).use {
            gson.fromJson(it.reader(), TaskItemsModel::class.java)
        }

        dao.insertAll(*network.tasks.toTypedArray())

        cached
    }

    fun getTaskDetails(id: String): Flow<TaskModel?> {
        return dao.get(id)
    }

    suspend fun updateTask(task: TaskModel) {
        dao.update(task)
    }

    companion object {
        private val gson =
            GsonBuilder().registerTypeAdapter(LocalDate::class.java, LocalDateConverter()).create()
    }
}
