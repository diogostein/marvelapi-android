package com.codelabs.marvelapi.core.models

data class Image(val path: String?, val extension: String?) {
    val url get() = "$path.$extension"
}
