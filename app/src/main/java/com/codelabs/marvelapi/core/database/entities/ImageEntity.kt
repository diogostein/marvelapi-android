package com.codelabs.marvelapi.core.database.entities

import androidx.room.ColumnInfo

data class ImageEntity(
    @ColumnInfo(name = "path") val path: String?,
    @ColumnInfo(name = "extension") val extension: String?,
)