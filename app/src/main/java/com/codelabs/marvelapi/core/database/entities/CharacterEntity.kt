package com.codelabs.marvelapi.core.database.entities

import androidx.room.*

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "description") val description: String?,
    @Embedded(prefix = "image_") val thumbnail: ImageEntity?
)
