package com.codelabs.marvelapi.features.characters.data

import com.codelabs.marvelapi.shared.data.ResultHandler
import com.codelabs.marvelapi.core.api.MarvelApiService
import com.codelabs.marvelapi.core.api.responses.*

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

    override suspend fun getCharacters(limit: Int, offset: Int, query: String?):
            DataWrapperResponse<CharacterResponse> {
        return ResultHandler.onRemoteDataSource {
            apiService.getCharacters(limit, offset, query)
        }
    }

    override suspend fun getCharacter(id: Int): DataWrapperResponse<CharacterResponse> {
        return ResultHandler.onRemoteDataSource {
            apiService.getCharacter(id)
        }
    }

    override suspend fun getCharacterComics(characterId: Int, limit: Int, offset: Int):
            DataWrapperResponse<ComicResponse> {
        return ResultHandler.onRemoteDataSource {
            apiService.getCharacterComics(characterId, limit, offset)
        }
    }

    override suspend fun getCharacterEvents(characterId: Int, limit: Int, offset: Int):
            DataWrapperResponse<EventResponse> {
        return ResultHandler.onRemoteDataSource {
            apiService.getCharacterEvents(characterId, limit, offset)
        }
    }

    override suspend fun getCharacterSeries(characterId: Int, limit: Int, offset: Int):
            DataWrapperResponse<SerieResponse> {
        return ResultHandler.onRemoteDataSource {
            apiService.getCharacterSeries(characterId, limit, offset)
        }
    }

}