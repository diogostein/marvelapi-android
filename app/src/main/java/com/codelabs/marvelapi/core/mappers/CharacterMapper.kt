package com.codelabs.marvelapi.core.mappers

import com.codelabs.marvelapi.core.api.responses.CharacterResponse
import com.codelabs.marvelapi.core.database.entities.CharacterEntity
import com.codelabs.marvelapi.core.models.Character
import com.codelabs.marvelapi.core.models.Image

object CharacterMapper {

    object ResponseToEntity : Mapper<CharacterResponse, CharacterEntity>() {
        override fun map(input: CharacterResponse): CharacterEntity {
            return CharacterEntity(
                id = input.id,
                name = input.name,
                description = input.description,
                thumbnail = if (input.thumbnail != null) ImageMapper.ResponseToEntity.map(input.thumbnail) else null //ImageMapper.map(input.thumbnail),
            )
        }
    }

    object EntityToModel : Mapper<CharacterEntity, Character>() {
        override fun map(input: CharacterEntity): Character {
            return Character(
                id = input.id,
                name = input.name,
                description = input.description,
                thumbnail = if (input.thumbnail != null) ImageMapper.EntityToModel.map(input.thumbnail) else null
            )
        }
    }

    fun mapResponseToEntity(input: CharacterResponse) = ResponseToEntity.map(input)
    fun mapResponseToEntity(input: List<CharacterResponse>) = ResponseToEntity.map(input)

    fun mapEntityToModel(input: CharacterEntity) = EntityToModel.map(input)
    fun mapEntityToModel(input: List<CharacterEntity>) = EntityToModel.map(input)

}
