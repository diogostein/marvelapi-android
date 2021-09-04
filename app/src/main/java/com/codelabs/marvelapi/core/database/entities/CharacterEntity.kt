package com.codelabs.marvelapi.core.database.entities

import androidx.room.*

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey @ColumnInfo(name = "character_id") val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "description") val description: String?,
    @Embedded(prefix = "image_") val thumbnail: ImageEntity?
)
