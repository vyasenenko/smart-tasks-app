package com.smart.tasks.domain.api

import com.smart.tasks.domain.model.TaskItemsModel
import retrofit2.http.GET

interface MainApi {

    @GET(".")
    suspend fun getTasks(): TaskItemsModel
}