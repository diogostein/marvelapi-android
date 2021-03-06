package com.codelabs.marvelapi.core.mappers

import com.codelabs.marvelapi.core.api.responses.ImageResponse
import com.codelabs.marvelapi.core.database.entities.ImageEntity
import com.codelabs.marvelapi.core.models.Image

object ImageMapper {

    object ResponseToEntity : Mapper<ImageResponse, ImageEntity>() {
        override fun map(input: ImageResponse): ImageEntity {
            return ImageEntity(path = input.path, extension = input.extension)
        }
    }

    object EntityToModel : Mapper<ImageEntity, Image>() {
        override fun map(input: ImageEntity): Image {
            return Image(path = input.path, extension = input.extension)
        }
    }
}