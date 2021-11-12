package com.codelabs.marvelapi.features.characters.data

import arrow.core.Either
import com.codelabs.marvelapi.core.errors.Failure
import com.codelabs.marvelapi.core.models.Character
import com.codelabs.marvelapi.core.models.Comic
import com.codelabs.marvelapi.core.models.Event
import com.codelabs.marvelapi.core.models.Serie

class FakeCharacterRepository : CharacterRepository {
    val fakeCharacters = listOf(
        Character(
            id = 1,
            name = "Spider-Man",
            description = "",
            thumbnail = null
        ),
        Character(
            id = 2,
            name = "Hulk",
            description = "",
            thumbnail = null
        ),
        Character(
            id = 3,
            name = "Iron-Man",
            description = "",
            thumbnail = null
        )
    )

    override suspend fun getCharacters(
        limit: Int,
        offset: Int,
        query: String?
    ): Either<Failure, List<Character>> {
        return Either.Right(fakeCharacters)
    }

    override suspend fun getCharacter(id: Int): Either<Failure, Character> {
        TODO("Not yet implemented")
    }

    override suspend fun getCharacterComics(
        characterId: Int,
        limit: Int,
        offset: Int
    ): Either<Failure, List<Comic>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCharacterEvents(
        characterId: Int,
        limit: Int,
        offset: Int
    ): Either<Failure, List<Event>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCharacterSeries(
        characterId: Int,
        limit: Int,
        offset: Int
    ): Either<Failure, List<Serie>> {
        TODO("Not yet implemented")
    }
}