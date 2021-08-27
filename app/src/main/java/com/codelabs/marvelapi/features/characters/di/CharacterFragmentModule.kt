package com.codelabs.marvelapi.features.characters.di

import com.codelabs.marvelapi.core.models.Character
import com.codelabs.marvelapi.features.characters.CharactersPagingAdapter
import com.codelabs.marvelapi.shared.pagination.PagingController
import com.codelabs.marvelapi.shared.pagination.PagingScrollHandler
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
    fun provideCharacterPagingController(
        charactersPagingAdapter: CharactersPagingAdapter,
        pagingScrollHandler: PagingScrollHandler,
    ): PagingController<Character, CharactersPagingAdapter.CharacterViewHolder> {
        return PagingController(charactersPagingAdapter, pagingScrollHandler)
    }

}