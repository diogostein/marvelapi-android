package com.codelabs.marvelapi.features.characters.data

import arrow.core.Either
import com.codelabs.marvelapi.core.api.MarvelApiErrorParser
import com.codelabs.marvelapi.core.api.MarvelApiService
import com.codelabs.marvelapi.core.api.responses.BaseResponse
import com.codelabs.marvelapi.core.api.responses.CharacterResponse
import com.codelabs.marvelapi.core.errors.Failure

interface CharacterRemoteDataSource {
    suspend fun getCharacters(): Either<Failure, BaseResponse<CharacterResponse>>
}

class CharacterRemoteDataSourceImpl(
    private val apiService: MarvelApiService,
) : CharacterRemoteDataSource {

    override suspend fun getCharacters(): Either<Failure, BaseResponse<CharacterResponse>> {
        val response = apiService.getCharacters()

        return if (response.isSuccessful)
            Either.Right(response.body()!!)
        else
            Either.Left(MarvelApiErrorParser.parse(response).getFailure())
    }

}