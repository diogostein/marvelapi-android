package com.codelabs.marvelapi.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "series")
data class SerieEntity(
    @PrimaryKey @ColumnInfo(name = "serie_id") val id: Int,
    @ColumnInfo(name = "title") val title: String?,
    @Embedded(prefix = "image_") val thumbnail: ImageEntity?
)