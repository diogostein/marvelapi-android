package com.codelabs.marvelapi.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comics")
data class ComicEntity(
    @PrimaryKey @ColumnInfo(name = "comic_id") val id: Int,
    @ColumnInfo(name = "title") val title: String?,
    @Embedded(prefix = "image_") val thumbnail: ImageEntity?
)