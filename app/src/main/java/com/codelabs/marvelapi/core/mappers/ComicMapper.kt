package com.codelabs.marvelapi.core.mappers

import com.codelabs.marvelapi.core.api.responses.ComicResponse
import com.codelabs.marvelapi.core.database.entities.ComicEntity
import com.codelabs.marvelapi.core.models.Comic

object ComicMapper {

    object ResponseToEntity : Mapper<ComicResponse, ComicEntity>() {
        override fun map(input: ComicResponse): ComicEntity {
            return ComicEntity(
                id = input.id,
                title = input.title,
                thumbnail = if (input.thumbnail != null) ImageMapper.ResponseToEntity.map(input.thumbnail) else null
            )
        }
    }

    object EntityToModel : Mapper<ComicEntity, Comic>() {
        override fun map(input: ComicEntity): Comic {
            return Comic(
                id = input.id,
                title = input.title,
                thumbnail = if (input.thumbnail != null) ImageMapper.EntityToModel.map(input.thumbnail) else null
            )
        }
    }

    fun mapResponseToEntity(input: ComicResponse) = ResponseToEntity.map(input)
    fun mapResponseToEntity(input: List<ComicResponse>) = ResponseToEntity.map(input)

    fun mapEntityToModel(input: ComicEntity) = EntityToModel.map(input)
    fun mapEntityToModel(input: List<ComicEntity>) = EntityToModel.map(input)

}