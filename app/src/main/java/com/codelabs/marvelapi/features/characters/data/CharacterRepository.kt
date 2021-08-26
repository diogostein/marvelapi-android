package com.codelabs.marvelapi.features.characters.data

import arrow.core.Either
import com.codelabs.marvelapi.core.errors.Failure
import com.codelabs.marvelapi.core.mappers.CharacterMapper
import com.codelabs.marvelapi.core.mappers.ComicMapper
import com.codelabs.marvelapi.core.mappers.EventMapper
import com.codelabs.marvelapi.core.models.Character
import com.codelabs.marvelapi.core.models.Comic
import com.codelabs.marvelapi.core.models.Event

interface CharacterRepository {
    suspend fun getCharacters(limit: Int, offset: Int, query: String?): Either<Failure, List<Character>>
    suspend fun getCharacter(id: Int): Either<Failure, Character>
    suspend fun getCharacterComics(characterId: Int, limit: Int, offset: Int): Either<Failure, List<Comic>>
    suspend fun getCharacterEvents(characterId: Int, limit: Int, offset: Int): Either<Failure, List<Event>>
}

class CharacterRepositoryImpl(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val characterMapper: CharacterMapper,
    private val comicMapper: ComicMapper,
    private val eventMapper: EventMapper,
) : CharacterRepository {

    override suspend fun getCharacters(limit: Int, offset: Int, query: String?): Either<Failure, List<Character>> {
        val result = remoteDataSource.getCharacters(limit, offset, query)

        return result.fold(
            { Either.Left(it) },
            { Either.Right(characterMapper.map(it.data.results)) }
        )
    }

    override suspend fun getCharacter(id: Int): Either<Failure, Character> {
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
        val result = remoteDataSource.getCharacterEvents(characterId, limit, offset)

        return result.fold(
            { Either.Left(it) },
            { Either.Right(eventMapper.map(it.data.results)) }
        )
    }
}