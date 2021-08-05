package com.codelabs.marvelapi.core.api

import com.codelabs.marvelapi.core.api.responses.CharacterResponse
import com.codelabs.marvelapi.core.api.responses.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface MarvelApiService {
    @GET("v1/public/characters")
    suspend fun getCharacters(): Response<BaseResponse<CharacterResponse>>
}