package com.codelabs.marvelapi.features.characters.di

import com.codelabs.marvelapi.core.api.MarvelApiService
import com.codelabs.marvelapi.core.mappers.CharacterMapper
import com.codelabs.marvelapi.features.characters.data.CharacterRemoteDataSource
import com.codelabs.marvelapi.features.characters.data.CharacterRemoteDataSourceImpl
import com.codelabs.marvelapi.features.characters.data.CharacterRepository
import com.codelabs.marvelapi.features.characters.data.CharacterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object CharacterModule {

    @ViewModelScoped
    @Provides
    fun provideCharacterMapper() = CharacterMapper()

    @ViewModelScoped
    @Provides
    fun provideCharacterRemoteDataSource(apiService: MarvelApiService): CharacterRemoteDataSource {
        return CharacterRemoteDataSourceImpl(apiService)
    }

    @ViewModelScoped
    @Provides
    fun provideCharacterRepository(
        characterRemoteDataSource: CharacterRemoteDataSource,
        characterMapper: CharacterMapper,
    ): CharacterRepository {
        return CharacterRepositoryImpl(characterRemoteDataSource, characterMapper)
    }

}