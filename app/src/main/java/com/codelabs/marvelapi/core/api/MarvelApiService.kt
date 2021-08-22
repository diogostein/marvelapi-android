package com.codelabs.marvelapi.core.api

import com.codelabs.marvelapi.core.api.responses.CharacterResponse
import com.codelabs.marvelapi.core.api.responses.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApiService {
    @GET("v1/public/characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("nameStartsWith") query: String?
    ): BaseResponse<CharacterResponse>

    @GET("v1/public/characters/{id}")
    suspend fun getCharacter(@Path("id") id: Int): BaseResponse<CharacterResponse>
}