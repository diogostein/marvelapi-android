package com.codelabs.marvelapi.features.characters.data

import arrow.core.Either
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
    private val characterMapper: CharacterMapper,
    private val comicMapper: ComicMapper,
    private val eventMapper: EventMapper,
    private val serieMapper: SerieMapper,
    private val networkHelper: NetworkHelper
) : CharacterRepository {

    override suspend fun getCharacters(limit: Int, offset: Int, query: String?): Either<Failure, List<Character>> {
        if (!networkHelper.isConnectionAvailable()) return Either.Left(Failure.NoConnection)

        val result = remoteDataSource.getCharacters(limit, offset, query)

        return result.fold(
            { Either.Left(it) },
            { Either.Right(characterMapper.map(it.data.results)) }
        )
    }

    override suspend fun getCharacter(id: Int): Either<Failure, Character> {
        if (!networkHelper.isConnectionAvailable()) return Either.Left(Failure.NoConnection)

        val result = remoteDataSource.getCharacter(id)

        return result.fold(
            { Either.Left(it) },
            { Either.Right(characterMapper.map(it.data.results.first())) }
        )
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