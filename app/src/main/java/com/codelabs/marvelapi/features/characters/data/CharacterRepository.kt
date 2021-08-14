package com.codelabs.marvelapi.features.characters.data

import arrow.core.Either
import com.codelabs.marvelapi.core.errors.Failure
import com.codelabs.marvelapi.core.mappers.CharacterMapper
import com.codelabs.marvelapi.core.models.Character

interface CharacterRepository {
    suspend fun getCharacters(limit: Int, offset: Int): Either<Failure, List<Character>>
}

class CharacterRepositoryImpl(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val characterMapper: CharacterMapper,
) : CharacterRepository {

    override suspend fun getCharacters(limit: Int, offset: Int): Either<Failure, List<Character>> {
        val result = remoteDataSource.getCharacters(limit, offset)

        return result.fold(
            { Either.Left(it) },
            { Either.Right(characterMapper.map(it.data.results)) }
        )
    }
}