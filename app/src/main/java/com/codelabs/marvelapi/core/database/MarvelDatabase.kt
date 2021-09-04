package com.codelabs.marvelapi.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codelabs.marvelapi.core.database.daos.CharacterDao
import com.codelabs.marvelapi.core.database.daos.ComicDao
import com.codelabs.marvelapi.core.database.daos.EventDao
import com.codelabs.marvelapi.core.database.daos.SerieDao
import com.codelabs.marvelapi.core.database.entities.*

@Database(
    entities = [
        CharacterEntity::class,
        ComicEntity::class,
        CharacterComicCrossRef::class,
        EventEntity::class,
        CharacterEventCrossRef::class,
        SerieEntity::class,
        CharacterSerieCrossRef::class
   ],
    version = 1,
    exportSchema = false
)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun comicDao(): ComicDao
    abstract fun eventDao(): EventDao
    abstract fun serieDao(): SerieDao
}