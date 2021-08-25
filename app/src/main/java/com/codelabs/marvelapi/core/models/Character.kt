package com.codelabs.marvelapi.core.models

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Image,
)