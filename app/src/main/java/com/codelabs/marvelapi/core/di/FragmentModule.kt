package com.codelabs.marvelapi.core.di

import com.codelabs.marvelapi.shared.pagination.PagingScrollHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @Provides
    fun providePagingScrollHandler() = PagingScrollHandler()

}