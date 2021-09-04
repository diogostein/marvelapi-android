package com.codelabs.marvelapi.core.database.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.codelabs.marvelapi.core.database.entities.*

data class CharacterWithSeries(
    @Embedded val character: CharacterEntity,
    @Relation(
        parentColumn = "character_id",
        entityColumn = "serie_id",
        associateBy = Junction(CharacterSerieCrossRef::class)
    )
    val series: List<SerieEntity>
)
