package com.codelabs.marvelapi.features.characters.data

import arrow.core.Either
import com.codelabs.marvelapi.core.api.MarvelApiErrorParser
import com.codelabs.marvelapi.core.api.MarvelApiService
import com.codelabs.marvelapi.core.api.responses.*
import com.codelabs.marvelapi.core.errors.CustomException
import com.codelabs.marvelapi.core.errors.Failure
import retrofit2.HttpException

interface CharacterRemoteDataSource {
    suspend fun getCharacters(limit: Int, offset: Int, query: String?):
            DataWrapperResponse<CharacterResponse>
    suspend fun getCharacter(id: Int):
            DataWrapperResponse<CharacterResponse>
    suspend fun getCharacterComics(characterId: Int, limit: Int, offset: Int):
            Either<Failure, DataWrapperResponse<ComicResponse>>
    suspend fun getCharacterEvents(characterId: Int, limit: Int, offset: Int):
            Either<Failure, DataWrapperResponse<EventResponse>>
    suspend fun getCharacterSeries(characterId: Int, limit: Int, offset: Int):
            Either<Failure, DataWrapperResponse<SerieResponse>>
}

class CharacterRemoteDataSourceImpl(
    private val apiService: MarvelApiService,
) : CharacterRemoteDataSource {

    override suspend fun getCharacters(limit: Int, offset: Int, query: String?): DataWrapperResponse<CharacterResponse> {
        return try {
            apiService.getCharacters(limit, offset, query)
        } catch (e: HttpException) {
            throw CustomException.Server(MarvelApiErrorParser.parse(e.response()!!))
        } catch (e: Exception) {
            throw CustomException(message = e.message)
        }
    }

    override suspend fun getCharacter(id: Int): DataWrapperResponse<CharacterResponse> {
        return try {
            apiService.getCharacter(id)
        } catch (e: HttpException) {
            throw CustomException.Server(MarvelApiErrorParser.parse(e.response()!!))
        } catch (e: Exception) {
            throw CustomException(message = e.message)
        }
    }

    override suspend fun getCharacterComics(characterId: Int, limit: Int, offset: Int):
            Either<Failure, DataWrapperResponse<ComicResponse>> {
        return try {
            val response = apiService.getCharacterComics(characterId, limit, offset)

            Either.Right(response)
        } catch (e: HttpException) {
            Either.Left(MarvelApiErrorParser.parse(e.response()!!).getFailureType())
        } catch (e: Exception) {
            Either.Left(Failure.Server(e.message))
        }
    }

    override suspend fun getCharacterEvents(characterId: Int, limit: Int, offset: Int):
            Either<Failure, DataWrapperResponse<EventResponse>> {
        return try {
            val response = apiService.getCharacterEvents(characterId, limit, offset)

            Either.Right(response)
        } catch (e: HttpException) {
            Either.Left(MarvelApiErrorParser.parse(e.response()!!).getFailureType())
        } catch (e: Exception) {
            Either.Left(Failure.Server(e.message))
        }
    }

    override suspend fun getCharacterSeries(characterId: Int, limit: Int, offset: Int):
            Either<Failure, DataWrapperResponse<SerieResponse>> {
        return try {
            val response = apiService.getCharacterSeries(characterId, limit, offset)

            Either.Right(response)
        } catch (e: HttpException) {
            Either.Left(MarvelApiErrorParser.parse(e.response()!!).getFailureType())
        } catch (e: Exception) {
            Either.Left(Failure.Server(e.message))
        }
    }

}