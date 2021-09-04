package com.codelabs.marvelapi.core.database.daos

import androidx.room.*
import com.codelabs.marvelapi.core.database.entities.ComicEntity

@Dao
abstract class ComicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(comic: ComicEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(comic: List<ComicEntity>): List<Long>

//    @Transaction
//    @Query("SELECT c.* FROM comics c " +
//            "INNER JOIN character_comic_refs ccr " +
//            "ON c.comic_id = ccr.comic_id " +
//            "WHERE ccr.character_id = :characterId " +
//            "ORDER BY c.title " +
//            "LIMIT :limit OFFSET :offset")
//    abstract suspend fun getComicsByCharacterId(
//        characterId: Int, limit: Int, offset: Int): List<ComicEntity>

}