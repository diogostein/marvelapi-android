package com.codelabs.marvelapi.core.database.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.codelabs.marvelapi.core.database.entities.*

data class CharacterWithEvents(
    @Embedded val character: CharacterEntity,
    @Relation(
        parentColumn = "character_id",
        entityColumn = "event_id",
        associateBy = Junction(CharacterEventCrossRef::class)
    )
    val events: List<EventEntity>
)
