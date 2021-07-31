package com.codelabs.marvelapi.core.api

import com.codelabs.marvelapi.core.api.responses.CharacterResponse
import com.codelabs.marvelapi.core.api.responses.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface MarvelApiService {
    @GET("v1/public/characters?ts=1&apikey=f6777526847fbca81c4df47c24fb34c4&hash=450fe9351bf08ecaef79c1c0aa14daee")
    suspend fun getCharacters(): Response<BaseResponse<CharacterResponse>>
}