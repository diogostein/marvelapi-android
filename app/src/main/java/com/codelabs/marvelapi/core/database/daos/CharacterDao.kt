package com.codelabs.marvelapi.core.database.daos

import androidx.room.*
import com.codelabs.marvelapi.core.database.entities.CharacterComicCrossRef
import com.codelabs.marvelapi.core.database.entities.CharacterEntity
import com.codelabs.marvelapi.core.database.entities.CharacterEventCrossRef
import com.codelabs.marvelapi.core.database.entities.CharacterSerieCrossRef
import com.codelabs.marvelapi.core.database.entities.relations.CharacterWithComics
import com.codelabs.marvelapi.core.database.entities.relations.CharacterWithEvents
import com.codelabs.marvelapi.core.database.entities.relations.CharacterWithSeries

@Dao
abstract class CharacterDao {

    @Query("SELECT * FROM characters ORDER BY name")
    abstract suspend fun all(): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE upper(name) LIKE :name || '%' ORDER BY name LIMIT :limit OFFSET :offset")
    abstract suspend fun where(limit: Int, offset: Int, name: String = ""): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE character_id = :id")
    abstract suspend fun findCharacterById(id: Int): CharacterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(character: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(character: List<CharacterEntity>)

    @Transaction
    @Query("SELECT * FROM characters WHERE character_id = :characterId")
    abstract suspend fun getCharacterWithComics(characterId: Int): CharacterWithComics

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertAllCharacterComicCrossRef(crossRef: List<CharacterComicCrossRef>)

    @Transaction
    @Query("SELECT * FROM characters WHERE character_id = :characterId")
    abstract suspend fun getCharacterWithEvents(characterId: Int): CharacterWithEvents

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertAllCharacterEventCrossRef(crossRef: List<CharacterEventCrossRef>)

    @Transaction
    @Query("SELECT * FROM characters WHERE character_id = :characterId")
    abstract suspend fun getCharacterWithSeries(characterId: Int): CharacterWithSeries

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertAllCharacterSerieCrossRef(crossRef: List<CharacterSerieCrossRef>)

}