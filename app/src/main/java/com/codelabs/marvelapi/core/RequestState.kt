package com.codelabs.marvelapi.core

sealed class RequestState {
    object Loading : RequestState()
    class Error(val message: String) : RequestState()
    class Completed<T>(val value: T) : RequestState()

    object PaginationLoading : RequestState()
    class PaginationError(val message: String) : RequestState()
    object PaginationEnded : RequestState()
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> RequestState.Completed<*>.castValue() = this as RequestState.Completed<T>