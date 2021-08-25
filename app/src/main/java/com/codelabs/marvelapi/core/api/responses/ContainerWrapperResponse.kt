package com.codelabs.marvelapi.core.api.responses

import com.squareup.moshi.Json

data class ContainerWrapperResponse<T>(@field:Json(name = "results") val results: List<T>)
