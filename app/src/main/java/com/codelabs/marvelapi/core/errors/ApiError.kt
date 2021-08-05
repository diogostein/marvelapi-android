package com.codelabs.marvelapi.core.errors

class ApiError(val code: String? = null, val message: String? = null) {

    fun getFailure(): Failure = when (code) {
        "InvalidCredentials" -> Failure.InvalidCredentials()
        else -> Failure.Server()
    }

}