package com.codelabs.marvelapi.core.mappers

import com.codelabs.marvelapi.core.api.responses.CharacterResponse
import com.codelabs.marvelapi.core.models.Character

class CharacterMapper : Mapper<CharacterResponse, Character>() {
    override fun map(input: CharacterResponse): Character {
        return Character(name = input.name)
    }
}
