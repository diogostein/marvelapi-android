package com.codelabs.marvelapi.core.api

import com.codelabs.marvelapi.core.api.interceptors.AuthorizationInterceptor
import com.codelabs.marvelapi.core.errors.ApiError
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


object MarvelApi {
    private val okHttp = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .addNetworkInterceptor(AuthorizationInterceptor())
            .build()

    private val retrofit = Retrofit.Builder()
            .client(okHttp)
            .baseUrl("http://gateway.marvel.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    val service: MarvelApiService = retrofit.create(MarvelApiService::class.java)

    fun parseError(response: retrofit2.Response<*>): ApiError {
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
