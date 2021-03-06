package com.codelabs.marvelapi.features.characters.data

import arrow.core.Either
import com.codelabs.marvelapi.shared.data.ResultHandler
import com.codelabs.marvelapi.core.errors.Failure
import com.codelabs.marvelapi.core.helpers.NetworkHelper
import com.codelabs.marvelapi.core.mappers.*
import com.codelabs.marvelapi.core.models.*

interface CharacterRepository {
    suspend fun getCharacters(limit: Int, offset: Int, query: String?): Either<Failure, List<Character>>
    suspend fun getCharacter(id: Int): Either<Failure, Character>
    suspend fun getCharacterComics(characterId: Int, limit: Int, offset: Int): Either<Failure, List<Comic>>
    suspend fun getCharacterEvents(characterId: Int, limit: Int, offset: Int): Either<Failure, List<Event>>
    suspend fun getCharacterSeries(characterId: Int, limit: Int, offset: Int): Either<Failure, List<Serie>>
}

class CharacterRepositoryImpl(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterLocalDataSource,
    private val characterMapper: CharacterMapper,
    private val comicMapper: ComicMapper,
    private val eventMapper: EventMapper,
    private val serieMapper: SerieMapper,
    private val networkHelper: NetworkHelper
) : CharacterRepository {

    override suspend fun getCharacters(limit: Int, offset: Int, query: String?): Either<Failure, List<Character>> {
        if (!networkHelper.isConnectionAvailable()) return Either.Left(Failure.NoConnection)

        return ResultHandler.onRepository {
            val result = remoteDataSource.getCharacters(limit, offset, query)
            val characterEntities = characterMapper.mapResponseToEntity(result.data.results)

            localDataSource.insertCharacters(characterEntities)

            Either.Right(characterMapper.mapEntityToModel(characterEntities))
        }
    }

    override suspend fun getCharacter(id: Int): Either<Failure, Character> {
        val character = localDataSource.getCharacter(id)

        if (character != null) {
            return Either.Right(characterMapper.mapEntityToModel(character))
        }

        if (!networkHelper.isConnectionAvailable()) {
            return Either.Left(Failure.NoConnection)
        }

        return ResultHandler.onRepository {
            val result = remoteDataSource.getCharacter(id)
            val characterEntity = characterMapper.mapResponseToEntity(result.data.results.first())

            localDataSource.insertCharacters(arrayListOf(characterEntity))

            Either.Right(characterMapper.mapEntityToModel(characterEntity))
        }
    }

    override suspend fun getCharacterComics(
        characterId: Int,
        limit: Int,
        offset: Int,
    ): Either<Failure, List<Comic>> {
        if (!networkHelper.isConnectionAvailable()) return Either.Left(Failure.NoConnection)

        return ResultHandler.onRepository {
            val result = remoteDataSource.getCharacterComics(characterId, limit, offset)
            val comicEntities = comicMapper.mapResponseToEntity(result.data.results)

            localDataSource.insertCharacterComics(characterId, comicEntities)

            Either.Right(comicMapper.mapEntityToModel(comicEntities))
        }
    }

    override suspend fun getCharacterEvents(
        characterId: Int,
        limit: Int,
        offset: Int
    ): Either<Failure, List<Event>> {
        if (!networkHelper.isConnectionAvailable()) return Either.Left(Failure.NoConnection)

        return ResultHandler.onRepository {
            val result = remoteDataSource.getCharacterEvents(characterId, limit, offset)
            val eventEntities = eventMapper.mapResponseToEntity(result.data.results)

            localDataSource.insertCharacterEvents(characterId, eventEntities)

            Either.Right(eventMapper.mapEntityToModel(eventEntities))
        }
    }

    override suspend fun getCharacterSeries(
        characterId: Int,
        limit: Int,
        offset: Int
    ): Either<Failure, List<Serie>> {
        if (!networkHelper.isConnectionAvailable()) return Either.Left(Failure.NoConnection)

        return ResultHandler.onRepository {
            val result = remoteDataSource.getCharacterSeries(characterId, limit, offset)
            val serieEntities = serieMapper.mapResponseToEntity(result.data.results)

            localDataSource.insertCharacterSeries(characterId, serieEntities)

            Either.Right(serieMapper.mapEntityToModel(serieEntities))
        }
    }

}