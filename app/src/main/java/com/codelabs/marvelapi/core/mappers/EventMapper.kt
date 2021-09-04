package com.codelabs.marvelapi.core.mappers

import com.codelabs.marvelapi.core.api.responses.EventResponse
import com.codelabs.marvelapi.core.database.entities.EventEntity
import com.codelabs.marvelapi.core.models.Event

object EventMapper {

    object ResponseToEntity : Mapper<EventResponse, EventEntity>() {
        override fun map(input: EventResponse): EventEntity {
            return EventEntity(
                id = input.id,
                title = input.title,
                thumbnail = if (input.thumbnail != null) ImageMapper.ResponseToEntity.map(input.thumbnail) else null
            )
        }
    }

    object EntityToModel : Mapper<EventEntity, Event>() {
        override fun map(input: EventEntity): Event {
            return Event(
                id = input.id,
                title = input.title,
                thumbnail = if (input.thumbnail != null) ImageMapper.EntityToModel.map(input.thumbnail) else null
            )
        }
    }

    fun mapResponseToEntity(input: EventResponse) = ResponseToEntity.map(input)
    fun mapResponseToEntity(input: List<EventResponse>) = ResponseToEntity.map(input)

    fun mapEntityToModel(input: EventEntity) = EntityToModel.map(input)
    fun mapEntityToModel(input: List<EventEntity>) = EntityToModel.map(input)

}