package com.smart.tasks.common.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


fun <T> safeApiCall(apiCall: suspend () -> T): Flow<State<T>> {
    return flow {
        emit(State.Loading(isLoading = true))
        try {
            emit(State.Success(apiCall.invoke()))
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    val errorMap = mutableMapOf<String, String>()
                    errorMap["Internet Connection Error"] = throwable.message!!
                    emit(State.NetworkError(errorMap))
                }

                is HttpException -> {
//                    val errorMap = mutableMapOf<String, String>()
//                    val errorResponse = convertErrorBody(throwable)
//                    errorMap[errorResponse?.errors.toString()] = errorResponse?.message.toString()
//                    emit(State.Error(errorMap))
                }

                else -> {
                    val errorMap = mutableMapOf<String, String>()
                    errorMap["Unknown error"] = throwable.message.toString()
                    emit(State.NetworkError(errorMap))
                }
            }
        }
    }
}