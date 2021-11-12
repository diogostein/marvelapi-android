package com.codelabs.marvelapi.core

sealed class ResultState<out T> {
    object Loading : ResultState<Nothing>()
    class Error(val message: String) : ResultState<Nothing>()
    class Completed<T>(val value: T) : ResultState<T>()

    object PaginationLoading : ResultState<Nothing>()
    class PaginationError(val message: String) : ResultState<Nothing>()
    object PaginationFinished : ResultState<Nothing>()
}

//@Suppress("UNCHECKED_CAST")
//inline fun <reified T : Any> RequestState.Completed<*>.castValue() = this as RequestState.Completed<T>