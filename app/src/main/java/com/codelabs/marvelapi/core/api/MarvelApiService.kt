package com.codelabs.marvelapi.core.api

import com.codelabs.marvelapi.core.api.responses.CharacterResponse
import com.codelabs.marvelapi.core.api.responses.ComicResponse
import com.codelabs.marvelapi.core.api.responses.DataWrapperResponse
import com.codelabs.marvelapi.core.api.responses.EventResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApiService {
    @GET("v1/public/characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("nameStartsWith") query: String?
    ): DataWrapperResponse<CharacterResponse>

    @GET("v1/public/characters/{id}")
    suspend fun getCharacter(@Path("id") id: Int): DataWrapperResponse<CharacterResponse>

    @GET("v1/public/characters/{character-id}/comics")
    suspend fun getCharacterComics(
        @Path("character-id") characterId: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): DataWrapperResponse<ComicResponse>

    @GET("v1/public/characters/{character-id}/events")
    suspend fun getCharacterEvents(
        @Path("character-id") characterId: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): DataWrapperResponse<EventResponse>
}