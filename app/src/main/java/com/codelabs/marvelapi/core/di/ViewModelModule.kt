package com.codelabs.marvelapi.core.di

import com.codelabs.marvelapi.core.mappers.CharacterMapper
import com.codelabs.marvelapi.core.mappers.ComicMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @ViewModelScoped
    @Provides
    fun provideCharacterMapper() = CharacterMapper

    @ViewModelScoped
    @Provides
    fun provideComicMapper() = ComicMapper

}