package com.example.moviedatabase.utils

sealed class Resources<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : Resources<T>(data = data)
    class Error<T>(message: String, data: T? = null) : Resources<T>(data = data, message = message)
    class Loading<T> : Resources<T>()
}