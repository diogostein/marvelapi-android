package com.codelabs.marvelapi.features.characterdetail.di

import com.codelabs.marvelapi.core.models.Comic
import com.codelabs.marvelapi.core.models.Event
import com.codelabs.marvelapi.core.models.Serie
import com.codelabs.marvelapi.features.characterdetail.adapters.ComicsPagingAdapter
import com.codelabs.marvelapi.features.characterdetail.adapters.EventsPagingAdapter
import com.codelabs.marvelapi.features.characterdetail.adapters.SeriesPagingAdapter
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
    fun provideEventsPagingAdapter() = EventsPagingAdapter()

    @FragmentScoped
    @Provides
    fun provideSeriesPagingAdapter() = SeriesPagingAdapter()

    @FragmentScoped
    @Provides
    fun provideComicsPaginationController(
        comicsPagingAdapter: ComicsPagingAdapter,
        paginationScrollHandler: PaginationScrollHandler,
    ): PaginationController<Comic, ComicsPagingAdapter.ComicViewHolder> {
        return PaginationController(comicsPagingAdapter, paginationScrollHandler)
    }

    @FragmentScoped
    @Provides
    fun provideEventsPaginationController(
        eventsPagingAdapter: EventsPagingAdapter,
        paginationScrollHandler: PaginationScrollHandler,
    ): PaginationController<Event, EventsPagingAdapter.EventViewHolder> {
        return PaginationController(eventsPagingAdapter, paginationScrollHandler)
    }

    @FragmentScoped
    @Provides
    fun provideSeriesPaginationController(
        seriesPagingAdapter: SeriesPagingAdapter,
        paginationScrollHandler: PaginationScrollHandler,
    ): PaginationController<Serie, SeriesPagingAdapter.SerieViewHolder> {
        return PaginationController(seriesPagingAdapter, paginationScrollHandler)
    }

}