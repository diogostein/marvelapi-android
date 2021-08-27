package com.codelabs.marvelapi.core.mappers

import com.codelabs.marvelapi.core.api.responses.ImageResponse
import com.codelabs.marvelapi.core.api.responses.StoryResponse
import com.codelabs.marvelapi.core.models.Story

object StoryMapper : Mapper<StoryResponse, Story>() {
    override fun map(input: StoryResponse): Story {
        return Story(
            id = input.id,
            title = input.title,
            thumbnail = ImageMapper.map(ImageResponse("", "")),
        )
    }
}