package com.codelabs.marvelapi.features.characters.di

import com.codelabs.marvelapi.core.models.Character
import com.codelabs.marvelapi.features.characters.CharactersPagingAdapter
import com.codelabs.marvelapi.shared.pagination.PaginationController
import com.codelabs.marvelapi.shared.pagination.PaginationScrollHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object CharacterFragmentModule {

    @FragmentScoped
    @Provides
    fun provideCharactersPagingAdapter() = CharactersPagingAdapter()

    @FragmentScoped
    @Provides
    fun providePaginationScrollHandler() = PaginationScrollHandler()

    @FragmentScoped
    @Provides
    fun providePaginationController(
        charactersPagingAdapter: CharactersPagingAdapter,
        paginationScrollHandler: PaginationScrollHandler,
    ): PaginationController<Character, CharactersPagingAdapter.CharacterViewHolder> {
        return PaginationController(charactersPagingAdapter, paginationScrollHandler)
    }

}