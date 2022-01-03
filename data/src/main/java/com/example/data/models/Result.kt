package com.example.data.models

sealed class Result<T> {

    class Loading<T> : Result<T>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val error: String?) : Result<T>()

    val isLoading: Boolean get() = this is Loading<T>
    val isSuccess: Boolean get() = this is Success<T>
    val isError: Boolean get() = this is Error<T>


    fun <R> map(function: (T) -> R): Result<R> {
        return when (this) {
            is Loading -> Loading()
            is Success -> Success(function(data))
            is Error -> Error(error)
        }
    }

}