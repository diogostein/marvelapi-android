package com.codelabs.marvelapi.core.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object MarvelApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://gateway.marvel.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val service: MarvelApiService = retrofit.create(MarvelApiService::class.java)
}
