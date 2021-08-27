package com.codelabs.marvelapi.core.mappers

import com.codelabs.marvelapi.core.api.responses.SerieResponse
import com.codelabs.marvelapi.core.models.Serie

object SerieMapper : Mapper<SerieResponse, Serie>() {
    override fun map(input: SerieResponse): Serie {
        return Serie(
            id = input.id,
            title = input.title,
            thumbnail = ImageMapper.map(input.thumbnail),
        )
    }
}