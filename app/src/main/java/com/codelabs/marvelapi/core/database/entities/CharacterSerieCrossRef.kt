package com.codelabs.marvelapi.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "character_serie_refs",
    primaryKeys = ["character_id", "serie_id"]
)
data class CharacterSerieCrossRef(
    @ColumnInfo(name = "character_id") val characterId: Int,
    @ColumnInfo(name = "serie_id") val serieId: Int
)