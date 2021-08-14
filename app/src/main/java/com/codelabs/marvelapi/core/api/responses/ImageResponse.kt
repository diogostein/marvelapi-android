package com.codelabs.marvelapi.core.api.responses

import com.squareup.moshi.Json

data class ImageResponse(
    @field:Json(name = "path") val path: String,
    @field:Json(name = "extension") val extension: String,
)
