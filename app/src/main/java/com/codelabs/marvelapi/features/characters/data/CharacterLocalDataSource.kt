package com.codelabs.marvelapi.features.characters.data

import com.codelabs.marvelapi.core.database.MarvelDatabase
import com.codelabs.marvelapi.core.database.entities.*

interface CharacterLocalDataSource {
    suspend fun getCharacters(limit: Int, offset: Int, query: String?): List<CharacterEntity>
    suspend fun getCharacter(id: Int): CharacterEntity?
    suspend fun insertCharacters(characters: List<CharacterEntity>)
    suspend fun insertCharacterComics(characterId: Int, comics: List<ComicEntity>)
    suspend fun insertCharacterEvents(characterId: Int, events: List<EventEntity>)
    suspend fun insertCharacterSeries(characterId: Int, series: List<SerieEntity>)
    suspend fun getCharacterComics(characterId: Int, limit: Int, offset: Int): List<ComicEntity>
    suspend fun getCharacterEvents(characterId: Int, limit: Int, offset: Int): List<EventEntity>
    suspend fun getCharacterSeries(characterId: Int, limit: Int, offset: Int): List<SerieEntity>
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

    override suspend fun insertCharacterComics(characterId: Int, comics: List<ComicEntity>) {
        val ids = database.comicDao().insertAll(comics)

        database.characterDao()
            .insertAllCharacterComicCrossRef(ids.map { CharacterComicCrossRef(characterId, it.toInt()) })
    }

    override suspend fun insertCharacterEvents(characterId: Int, events: List<EventEntity>) {
        val ids = database.eventDao().insertAll(events)

        database.characterDao()
            .insertAllCharacterEventCrossRef(ids.map { CharacterEventCrossRef(characterId, it.toInt()) })
    }

    override suspend fun insertCharacterSeries(characterId: Int, series: List<SerieEntity>) {
        val ids = database.serieDao().insertAll(series)

        database.characterDao()
            .insertAllCharacterSerieCrossRef(ids.map { CharacterSerieCrossRef(characterId, it.toInt()) })
    }

    override suspend fun getCharacterComics(
        characterId: Int,
        limit: Int,
        offset: Int
    ): List<ComicEntity> {
       // return database.comicDao().getComicsByCharacterId(characterId, limit, offset)
        val characterWithComics = database.characterDao().getCharacterWithComics(characterId)

        return characterWithComics.comics
    }

    override suspend fun getCharacterEvents(
        characterId: Int,
        limit: Int,
        offset: Int
    ): List<EventEntity> {
        val characterWithEvents = database.characterDao().getCharacterWithEvents(characterId)

        return characterWithEvents.events
    }

    override suspend fun getCharacterSeries(
        characterId: Int,
        limit: Int,
        offset: Int
    ): List<SerieEntity> {
        val characterWithSeries = database.characterDao().getCharacterWithSeries(characterId)

        return characterWithSeries.series
    }

}