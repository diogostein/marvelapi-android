package com.codelabs.marvelapi.features.characterdetail.di

import com.codelabs.marvelapi.core.models.Comic
import com.codelabs.marvelapi.core.models.Event
import com.codelabs.marvelapi.core.models.Serie
import com.codelabs.marvelapi.features.characterdetail.adapters.ComicsPagingAdapter
import com.codelabs.marvelapi.features.characterdetail.adapters.EventsPagingAdapter
import com.codelabs.marvelapi.features.characterdetail.adapters.SeriesPagingAdapter
import com.codelabs.marvelapi.shared.pagination.PagingController
import com.codelabs.marvelapi.shared.pagination.PagingScrollHandler
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
    fun provideComicsPagingController(
        comicsPagingAdapter: ComicsPagingAdapter,
        pagingScrollHandler: PagingScrollHandler,
    ): PagingController<Comic, ComicsPagingAdapter.ComicViewHolder> {
        return PagingController(comicsPagingAdapter, pagingScrollHandler)
    }

    @FragmentScoped
    @Provides
    fun provideEventsPagingController(
        eventsPagingAdapter: EventsPagingAdapter,
        pagingScrollHandler: PagingScrollHandler,
    ): PagingController<Event, EventsPagingAdapter.EventViewHolder> {
        return PagingController(eventsPagingAdapter, pagingScrollHandler)
    }

    @FragmentScoped
    @Provides
    fun provideSeriesPagingController(
        seriesPagingAdapter: SeriesPagingAdapter,
        pagingScrollHandler: PagingScrollHandler,
    ): PagingController<Serie, SeriesPagingAdapter.SerieViewHolder> {
        return PagingController(seriesPagingAdapter, pagingScrollHandler)
    }

}