package com.codelabs.marvelapi.core.errors

sealed class Failure(customMessage: String? = null) {
    class Server(customMessage: String? = null) : Failure(customMessage)
    class InvalidCredentials(customMessage: String? = null) : Failure(customMessage)
    class NoConnection : Failure()

    val message = customMessage ?: getDefaultMessage()

    private fun getDefaultMessage(): String = when (this) {
        is NoConnection -> "Sem conexão com a internet."
        is InvalidCredentials -> "Credenciais inválidas."
        is Server -> "Falha ao acessar servidor.\nVerifique sua conexão ou tente mais tarde."
    }
}

