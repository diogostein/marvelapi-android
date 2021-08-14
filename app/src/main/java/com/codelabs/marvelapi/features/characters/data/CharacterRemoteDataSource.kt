package com.codelabs.marvelapi.features.characters.data

import arrow.core.Either
import com.codelabs.marvelapi.core.api.MarvelApiErrorParser
import com.codelabs.marvelapi.core.api.MarvelApiService
import com.codelabs.marvelapi.core.api.responses.BaseResponse
import com.codelabs.marvelapi.core.api.responses.CharacterResponse
import com.codelabs.marvelapi.core.errors.Failure
import retrofit2.HttpException

interface CharacterRemoteDataSource {
    suspend fun getCharacters(limit: Int, offset: Int): Either<Failure, BaseResponse<CharacterResponse>>
}

class CharacterRemoteDataSourceImpl(
    private val apiService: MarvelApiService,
) : CharacterRemoteDataSource {

    override suspend fun getCharacters(limit: Int, offset: Int): Either<Failure, BaseResponse<CharacterResponse>> {
        return try {
            val response = apiService.getCharacters(limit, offset)

            Either.Right(response)
        } catch (e: HttpException) {
            Either.Left(MarvelApiErrorParser.parse(e.response()!!).getFailure())
        } catch (e: Exception) {
            Either.Left(Failure.Server(e.message))
        }
    }

}