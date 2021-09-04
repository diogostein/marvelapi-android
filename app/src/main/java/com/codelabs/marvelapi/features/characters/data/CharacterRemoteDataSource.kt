package com.codelabs.marvelapi.features.characters.data

import com.codelabs.marvelapi.core.api.MarvelApiErrorParser
import com.codelabs.marvelapi.core.api.MarvelApiService
import com.codelabs.marvelapi.core.api.responses.*
import com.codelabs.marvelapi.core.errors.CustomException
import retrofit2.HttpException

interface CharacterRemoteDataSource {
    suspend fun getCharacters(limit: Int, offset: Int, query: String?):
            DataWrapperResponse<CharacterResponse>
    suspend fun getCharacter(id: Int):
            DataWrapperResponse<CharacterResponse>
    suspend fun getCharacterComics(characterId: Int, limit: Int, offset: Int):
            DataWrapperResponse<ComicResponse>
    suspend fun getCharacterEvents(characterId: Int, limit: Int, offset: Int):
            DataWrapperResponse<EventResponse>
    suspend fun getCharacterSeries(characterId: Int, limit: Int, offset: Int):
            DataWrapperResponse<SerieResponse>
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
            DataWrapperResponse<ComicResponse> {
        return try {
            apiService.getCharacterComics(characterId, limit, offset)
        } catch (e: HttpException) {
            throw CustomException.Server(MarvelApiErrorParser.parse(e.response()!!))
        } catch (e: Exception) {
            throw CustomException(message = e.message)
        }
    }

    override suspend fun getCharacterEvents(characterId: Int, limit: Int, offset: Int):
            DataWrapperResponse<EventResponse> {
        return try {
            apiService.getCharacterEvents(characterId, limit, offset)
        } catch (e: HttpException) {
            throw CustomException.Server(MarvelApiErrorParser.parse(e.response()!!))
        } catch (e: Exception) {
            throw CustomException(message = e.message)
        }
    }

    override suspend fun getCharacterSeries(characterId: Int, limit: Int, offset: Int):
            DataWrapperResponse<SerieResponse> {
        return try {
            apiService.getCharacterSeries(characterId, limit, offset)
        } catch (e: HttpException) {
            throw CustomException.Server(MarvelApiErrorParser.parse(e.response()!!))
        } catch (e: Exception) {
            throw CustomException(message = e.message)
        }
    }

}