package com.codelabs.marvelapi.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "character_comic_refs",
    primaryKeys = ["character_id", "comic_id"]
)
data class CharacterComicCrossRef(
    @ColumnInfo(name = "character_id") val characterId: Int,
    @ColumnInfo(name = "comic_id") val comicId: Int
)