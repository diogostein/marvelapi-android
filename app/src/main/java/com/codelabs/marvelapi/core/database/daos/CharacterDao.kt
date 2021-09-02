package com.codelabs.marvelapi.core.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codelabs.marvelapi.core.database.entities.CharacterEntity

@Dao
abstract class CharacterDao {

    @Query("SELECT * FROM characters ORDER BY name")
    abstract suspend fun all(): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE upper(name) LIKE :name || '%' ORDER BY name LIMIT :limit OFFSET :offset")
    abstract suspend fun where(limit: Int, offset: Int, name: String = ""): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE id = :id")
    abstract suspend fun findCharacterById(id: Int): CharacterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(character: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(character: List<CharacterEntity>)

}