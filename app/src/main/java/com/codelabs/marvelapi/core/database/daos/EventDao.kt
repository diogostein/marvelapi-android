package com.codelabs.marvelapi.core.database.daos

import androidx.room.*
import com.codelabs.marvelapi.core.database.entities.EventEntity

@Dao
abstract class EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(event: EventEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(events: List<EventEntity>): List<Long>

}