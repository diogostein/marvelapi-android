package com.codelabs.marvelapi.core.api.responses

import com.squareup.moshi.Json

data class DataWrapperResponse<T>(
    @field:Json(name = "code") val code: Int,
    @field:Json(name = "data") val data: ContainerWrapperResponse<T>)
