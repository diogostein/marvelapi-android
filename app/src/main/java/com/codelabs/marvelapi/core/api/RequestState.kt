package com.codelabs.marvelapi.core.api

sealed class RequestState {
    object Loading : RequestState()
    data class Error(val message: String) : RequestState()
    data class Completed<T>(val value: T) : RequestState()
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> RequestState.Completed<*>.castValue() = this as RequestState.Completed<T>