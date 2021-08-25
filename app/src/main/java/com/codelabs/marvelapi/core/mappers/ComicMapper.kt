package com.codelabs.marvelapi.core.mappers

import com.codelabs.marvelapi.core.api.responses.ComicResponse
import com.codelabs.marvelapi.core.models.Comic

object ComicMapper : Mapper<ComicResponse, Comic>() {
    override fun map(input: ComicResponse): Comic {
        return Comic(
            id = input.id,
            title = input.title,
            thumbnail = ImageMapper.map(input.thumbnail),
        )
    }
}