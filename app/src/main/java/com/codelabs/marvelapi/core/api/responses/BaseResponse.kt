package com.codelabs.marvelapi.core.api.responses

import com.squareup.moshi.Json

data class BaseResponse<T>(
    @field:Json(name = "code") val code: Int,
    @field:Json(name = "data") val data: MetaResponse<T>)
