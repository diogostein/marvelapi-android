package com.codelabs.marvelapi.features.characters.di

import com.codelabs.marvelapi.core.api.MarvelApiService
import com.codelabs.marvelapi.core.database.MarvelDatabase
import com.codelabs.marvelapi.core.helpers.NetworkHelper
import com.codelabs.marvelapi.core.mappers.*
import com.codelabs.marvelapi.features.characters.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object CharacterViewModelModule {

    @ViewModelScoped
    @Provides
    fun provideCharacterRemoteDataSource(apiService: MarvelApiService): CharacterRemoteDataSource =
        CharacterRemoteDataSourceImpl(apiService)

    @ViewModelScoped
    @Provides
    fun provideCharacterLocalDataSource(database: MarvelDatabase): CharacterLocalDataSource =
        CharacterLocalDataSourceImpl(database)

    @ViewModelScoped
    @Provides
    fun provideCharacterRepository(
        characterRemoteDataSource: CharacterRemoteDataSource,
        characterLocalDataSource: CharacterLocalDataSource,
        characterMapper: CharacterMapper,
        comicMapper: ComicMapper,
        eventMapper: EventMapper,
        serieMapper: SerieMapper,
        networkHelper: NetworkHelper
    ): CharacterRepository {
        return CharacterRepositoryImpl(
            characterRemoteDataSource,
            characterLocalDataSource,
            characterMapper,
            comicMapper,
            eventMapper,
            serieMapper,
            networkHelper
        )
    }

}