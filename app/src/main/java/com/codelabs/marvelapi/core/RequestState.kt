package com.codelabs.marvelapi.core

sealed class RequestState<out T> {
    object Loading : RequestState<Nothing>()
    class Error(val message: String) : RequestState<Nothing>()
    class Completed<C>(val value: C) : RequestState<C>()

    object PaginationLoading : RequestState<Nothing>()
    class PaginationError(val message: String) : RequestState<Nothing>()
    object PaginationFinished : RequestState<Nothing>()
}

//@Suppress("UNCHECKED_CAST")
//inline fun <reified T : Any> RequestState.Completed<*>.castValue() = this as RequestState.Completed<T>