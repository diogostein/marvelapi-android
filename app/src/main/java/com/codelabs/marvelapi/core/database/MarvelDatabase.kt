package com.codelabs.marvelapi.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codelabs.marvelapi.core.database.daos.CharacterDao
import com.codelabs.marvelapi.core.database.entities.CharacterEntity
import com.codelabs.marvelapi.core.database.entities.ImageEntity

@Database(
    entities = [
        CharacterEntity::class,
   ],
    version = 1,
    exportSchema = false
)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}