package com.codelabs.marvelapi.core.utils

import java.security.MessageDigest

object DigestUtils {
    fun md5(input: String): String = MessageDigest
        .getInstance("MD5")
        .digest(input.toByteArray())
        .toHex()

    private fun ByteArray.toHex(): String = joinToString("") { "%02x".format(it) }
}