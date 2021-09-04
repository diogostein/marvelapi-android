package com.codelabs.marvelapi.core.mappers

import com.codelabs.marvelapi.core.api.responses.SerieResponse
import com.codelabs.marvelapi.core.database.entities.SerieEntity
import com.codelabs.marvelapi.core.models.Serie

object SerieMapper {

    object ResponseToEntity : Mapper<SerieResponse, SerieEntity>() {
        override fun map(input: SerieResponse): SerieEntity {
            return SerieEntity(
                id = input.id,
                title = input.title,
                thumbnail = if (input.thumbnail != null) ImageMapper.ResponseToEntity.map(input.thumbnail) else null
            )
        }
    }

    object EntityToModel : Mapper<SerieEntity, Serie>() {
        override fun map(input: SerieEntity): Serie {
            return Serie(
                id = input.id,
                title = input.title,
                thumbnail = if (input.thumbnail != null) ImageMapper.EntityToModel.map(input.thumbnail) else null
            )
        }
    }

    fun mapResponseToEntity(input: SerieResponse) = ResponseToEntity.map(input)
    fun mapResponseToEntity(input: List<SerieResponse>) = ResponseToEntity.map(input)

    fun mapEntityToModel(input: SerieEntity) = EntityToModel.map(input)
    fun mapEntityToModel(input: List<SerieEntity>) = EntityToModel.map(input)

}