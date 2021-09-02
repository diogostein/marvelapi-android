package com.codelabs.marvelapi.features.characters.data

import arrow.core.Either
import com.codelabs.marvelapi.core.errors.CustomException
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

        return try {
            val result = remoteDataSource.getCharacters(limit, offset, query)
            val characterEntities = characterMapper.mapResponseToEntity(result.data.results)

            localDataSource.insertCharacters(characterEntities)

            Either.Right(characterMapper.mapEntityToModel(characterEntities))
        } catch (e: CustomException.Server) {
            Either.Left(e.apiError.getFailureType())
        } catch (e: CustomException) {
            Either.Left(Failure(e.message))
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

        return try {
            val result = remoteDataSource.getCharacter(id)
            val characterEntity = characterMapper.mapResponseToEntity(result.data.results.first())

            localDataSource.insertCharacters(arrayListOf(characterEntity))

            Either.Right(characterMapper.mapEntityToModel(characterEntity))
        } catch (e: CustomException.Server) {
            Either.Left(e.apiError.getFailureType())
        } catch (e: CustomException) {
            Either.Left(Failure(e.message))
        }
    }

    override suspend fun getCharacterComics(
        characterId: Int,
        limit: Int,
        offset: Int,
    ): Either<Failure, List<Comic>> {
        if (!networkHelper.isConnectionAvailable()) return Either.Left(Failure.NoConnection)

        val result = remoteDataSource.getCharacterComics(characterId, limit, offset)

        return result.fold(
            { Either.Left(it) },
            { Either.Right(comicMapper.map(it.data.results)) }
        )
    }

    override suspend fun getCharacterEvents(
        characterId: Int,
        limit: Int,
        offset: Int
    ): Either<Failure, List<Event>> {
        if (!networkHelper.isConnectionAvailable()) return Either.Left(Failure.NoConnection)

        val result = remoteDataSource.getCharacterEvents(characterId, limit, offset)

        return result.fold(
            { Either.Left(it) },
            { Either.Right(eventMapper.map(it.data.results)) }
        )
    }

    override suspend fun getCharacterSeries(
        characterId: Int,
        limit: Int,
        offset: Int
    ): Either<Failure, List<Serie>> {
        if (!networkHelper.isConnectionAvailable()) return Either.Left(Failure.NoConnection)

        val result = remoteDataSource.getCharacterSeries(characterId, limit, offset)

        return result.fold(
            { Either.Left(it) },
            { Either.Right(serieMapper.map(it.data.results)) }
        )
    }

}