package com.codelabs.marvelapi.core.di

import com.codelabs.marvelapi.shared.pagination.PaginationScrollHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @FragmentScoped
    @Provides
    fun providePaginationScrollHandler() = PaginationScrollHandler()

}