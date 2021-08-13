package com.codelabs.marvelapi.core.api

import com.codelabs.marvelapi.core.errors.ApiError
import com.squareup.moshi.Moshi

object MarvelApiErrorParser {
    fun parse(response: retrofit2.Response<*>): ApiError {
        return try {
            Moshi.Builder()
                .build()
                .adapter(ApiError::class.java)
                .fromJson(response.errorBody()!!.source())!!
        } catch (e: Exception) {
            e.printStackTrace()
            ApiError()
        }
    }
}
