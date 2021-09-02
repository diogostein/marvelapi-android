package com.codelabs.marvelapi.core.errors

open class CustomException(
    override val message: String? = null,
) : Exception() {
    class Server(val apiError: ApiError) : CustomException()
}