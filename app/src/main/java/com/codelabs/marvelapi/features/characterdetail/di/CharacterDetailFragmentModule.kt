package com.codelabs.marvelapi.features.characterdetail.di

import com.codelabs.marvelapi.core.models.Comic
import com.codelabs.marvelapi.features.characterdetail.adapters.ComicsPagingAdapter
import com.codelabs.marvelapi.shared.pagination.PaginationController
import com.codelabs.marvelapi.shared.pagination.PaginationScrollHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object CharacterDetailFragmentModule {

    @FragmentScoped
    @Provides
    fun provideComicsPagingAdapter() = ComicsPagingAdapter()

    @FragmentScoped
    @Provides
    fun providePaginationController(
        comicsPagingAdapter: ComicsPagingAdapter,
        paginationScrollHandler: PaginationScrollHandler,
    ): PaginationController<Comic, ComicsPagingAdapter.ComicViewHolder> {
        return PaginationController(comicsPagingAdapter, paginationScrollHandler)
    }

}