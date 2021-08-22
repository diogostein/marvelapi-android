package com.codelabs.marvelapi.features.characters.data

import arrow.core.Either
import com.codelabs.marvelapi.core.errors.Failure
import com.codelabs.marvelapi.core.mappers.CharacterMapper
import com.codelabs.marvelapi.core.models.Character

interface CharacterRepository {
    suspend fun getCharacters(limit: Int, offset: Int, query: String?): Either<Failure, List<Character>>
    suspend fun getCharacter(id: Int): Either<Failure, Character>
}

class CharacterRepositoryImpl(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val characterMapper: CharacterMapper,
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
}