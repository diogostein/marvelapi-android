package com.codelabs.marvelapi.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "character_event_refs",
    primaryKeys = ["character_id", "event_id"]
)
data class CharacterEventCrossRef(
    @ColumnInfo(name = "character_id") val characterId: Int,
    @ColumnInfo(name = "event_id") val eventId: Int
)