package com.codelabs.marvelapi.features.characters.data

import com.codelabs.marvelapi.core.database.MarvelDatabase
import com.codelabs.marvelapi.core.database.entities.CharacterEntity

interface CharacterLocalDataSource {
    suspend fun getCharacters(limit: Int, offset: Int, query: String?): List<CharacterEntity>
    suspend fun getCharacter(id: Int): CharacterEntity?
    suspend fun insertCharacters(characters: List<CharacterEntity>)
}

class CharacterLocalDataSourceImpl(private val database: MarvelDatabase) : CharacterLocalDataSource {

    override suspend fun getCharacters(
        limit: Int,
        offset: Int,
        query: String?
    ): List<CharacterEntity> {
        return database.characterDao().where(limit, offset, query ?: "")
    }

    override suspend fun getCharacter(id: Int): CharacterEntity? {
        return database.characterDao().findCharacterById(id)
    }

    override suspend fun insertCharacters(characters: List<CharacterEntity>) {
        database.characterDao().insertAll(characters)
    }

}