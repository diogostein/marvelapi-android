package com.codelabs.marvelapi.core.di

import android.content.Context
import com.codelabs.marvelapi.core.helpers.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Singleton
    @Provides
    fun provideNetworkHelper(@ApplicationContext context: Context) = NetworkHelper(context)

}