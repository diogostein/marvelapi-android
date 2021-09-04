package com.codelabs.marvelapi.core.api.responses

import com.squareup.moshi.Json

data class ComicResponse(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "thumbnail") val thumbnail: ImageResponse?,
)