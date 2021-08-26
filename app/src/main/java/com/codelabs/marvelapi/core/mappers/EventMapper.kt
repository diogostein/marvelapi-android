package com.codelabs.marvelapi.core.mappers

import com.codelabs.marvelapi.core.api.responses.EventResponse
import com.codelabs.marvelapi.core.models.Event

object EventMapper : Mapper<EventResponse, Event>() {
    override fun map(input: EventResponse): Event {
        return Event(
            id = input.id,
            title = input.title,
            thumbnail = ImageMapper.map(input.thumbnail),
        )
    }
}