package com.codelabs.marvelapi.core.database.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.codelabs.marvelapi.core.database.entities.CharacterComicCrossRef
import com.codelabs.marvelapi.core.database.entities.CharacterEntity
import com.codelabs.marvelapi.core.database.entities.ComicEntity

data class CharacterWithComics(
    @Embedded val character: CharacterEntity,
    @Relation(
        parentColumn = "character_id",
        entityColumn = "comic_id",
        associateBy = Junction(CharacterComicCrossRef::class)
    )
    val comics: List<ComicEntity>
)
