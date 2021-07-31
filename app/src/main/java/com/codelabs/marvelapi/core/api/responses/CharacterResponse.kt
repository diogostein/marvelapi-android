package com.codelabs.marvelapi.core.api.responses

import com.squareup.moshi.Json

data class CharacterResponse(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "description") val description: String
    )