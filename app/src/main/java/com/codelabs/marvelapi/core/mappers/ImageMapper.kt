package com.codelabs.marvelapi.core.mappers

import com.codelabs.marvelapi.core.api.responses.ImageResponse
import com.codelabs.marvelapi.core.models.Image

object ImageMapper : Mapper<ImageResponse, Image>() {
    override fun map(input: ImageResponse): Image {
        return Image(path = input.path, extension = input.extension)
    }
}