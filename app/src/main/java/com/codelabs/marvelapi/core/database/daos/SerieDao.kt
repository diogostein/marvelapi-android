package com.codelabs.marvelapi.core.database.daos

import androidx.room.*
import com.codelabs.marvelapi.core.database.entities.SerieEntity

@Dao
abstract class SerieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(serie: SerieEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(series: List<SerieEntity>): List<Long>

}