package com.codelabs.marvelapi.features.characters.data

import arrow.core.Either
import com.codelabs.marvelapi.core.api.MarvelApiErrorParser
import com.codelabs.marvelapi.core.api.MarvelApiService
import com.codelabs.marvelapi.core.api.responses.*
import com.codelabs.marvelapi.core.errors.Failure
import retrofit2.HttpException

interface CharacterRemoteDataSource {
    suspend fun getCharacters(limit: Int, offset: Int, query: String?):
            Either<Failure, DataWrapperResponse<CharacterResponse>>
    suspend fun getCharacter(id: Int):
            Either<Failure, DataWrapperResponse<CharacterResponse>>
    suspend fun getCharacterComics(characterId: Int, limit: Int, offset: Int):
            Either<Failure, DataWrapperResponse<ComicResponse>>
    suspend fun getCharacterEvents(characterId: Int, limit: Int, offset: Int):
            Either<Failure, DataWrapperResponse<EventResponse>>
    suspend fun getCharacterSeries(characterId: Int, limit: Int, offset: Int):
            Either<Failure, DataWrapperResponse<SerieResponse>>
    suspend fun getCharacterStories(characterId: Int, limit: Int, offset: Int):
            Either<Failure, DataWrapperResponse<StoryResponse>>
}

class CharacterRemoteDataSourceImpl(
    private val apiService: MarvelApiService,
) : CharacterRemoteDataSource {

    override suspend fun getCharacters(limit: Int, offset: Int, query: String?): Either<Failure, DataWrapperResponse<CharacterResponse>> {
        return try {
            val response = apiService.getCharacters(limit, offset, query)

            Either.Right(response)
        } catch (e: HttpException) {
            Either.Left(MarvelApiErrorParser.parse(e.response()!!).getFailure())
        } catch (e: Exception) {
            Either.Left(Failure.Server(e.message))
        }
    }

    override suspend fun getCharacter(id: Int): Either<Failure, DataWrapperResponse<CharacterResponse>> {
        return try {
            val response = apiService.getCharacter(id)

            Either.Right(response)
        } catch (e: HttpException) {
            Either.Left(MarvelApiErrorParser.parse(e.response()!!).getFailure())
        } catch (e: Exception) {
            Either.Left(Failure.Server(e.message))
        }
    }

    override suspend fun getCharacterComics(characterId: Int, limit: Int, offset: Int):
            Either<Failure, DataWrapperResponse<ComicResponse>> {
        return try {
            val response = apiService.getCharacterComics(characterId, limit, offset)

            Either.Right(response)
        } catch (e: HttpException) {
            Either.Left(MarvelApiErrorParser.parse(e.response()!!).getFailure())
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
            Either.Left(MarvelApiErrorParser.parse(e.response()!!).getFailure())
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
            Either.Left(MarvelApiErrorParser.parse(e.response()!!).getFailure())
        } catch (e: Exception) {
            Either.Left(Failure.Server(e.message))
        }
    }

    override suspend fun getCharacterStories(characterId: Int, limit: Int, offset: Int):
            Either<Failure, DataWrapperResponse<StoryResponse>> {
        return try {
            val response = apiService.getCharacterStories(characterId, limit, offset)

            Either.Right(response)
        } catch (e: HttpException) {
            Either.Left(MarvelApiErrorParser.parse(e.response()!!).getFailure())
        } catch (e: Exception) {
            Either.Left(Failure.Server(e.message))
        }
    }

}