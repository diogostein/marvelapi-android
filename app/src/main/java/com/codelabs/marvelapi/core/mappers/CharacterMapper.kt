package com.codelabs.marvelapi.core.mappers

import com.codelabs.marvelapi.core.api.responses.CharacterResponse
import com.codelabs.marvelapi.core.models.Character

object CharacterMapper : Mapper<CharacterResponse, Character>() {
    override fun map(input: CharacterResponse): Character {
        return Character(
            id = input.id,
            name = input.name,
            description = input.description,
            thumbnail = ImageMapper.map(input.thumbnail),
        )
    }
}
