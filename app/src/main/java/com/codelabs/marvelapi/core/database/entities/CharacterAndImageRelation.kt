package com.codelabs.marvelapi.core.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CharacterAndImageRelation(
    @Embedded val thumbnail: ImageEntity,

    @Relation(parentColumn = "uid", entityColumn = "thumbnail_id")
    val character: CharacterEntity
)