package com.smart.tasks.common.flow

sealed class State<out T>(val data: T? = null, val message: Map<String, String>? = null)
{
    class Loading<T>(val isLoading: Boolean = true): State<T>(null)
    class Success<T>(data: T?): State<T>(data)
    class Error<T>(message: Map<String, String>?, data: T? = null): State<T>(data, message)
    class NetworkError<T>(message: Map<String, String>?, data: T? = null): State<T>(data, message)
}