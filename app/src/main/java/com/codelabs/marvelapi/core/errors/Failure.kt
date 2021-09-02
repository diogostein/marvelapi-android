package com.codelabs.marvelapi.core.errors

import com.codelabs.marvelapi.MarvelApplication
import com.codelabs.marvelapi.R

open class Failure(customMessage: String? = null) {
    class Server(customMessage: String? = null) : Failure(customMessage)
    class Database(customMessage: String? = null) : Failure(customMessage)
    class InvalidCredentials(customMessage: String? = null) : Failure(customMessage)
    object NoConnection : Failure()

    val message = customMessage ?: getDefaultMessage()

    private fun getDefaultMessage() = MarvelApplication.instance.getString(when (this) {
        is NoConnection -> R.string.no_internet_connection
        is InvalidCredentials -> R.string.invalid_credentials
        is Server -> R.string.failed_to_access_server
        is Database -> R.string.failed_to_access_database
        else -> R.string.unknown_fail
    })
}

