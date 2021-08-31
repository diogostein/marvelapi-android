package com.codelabs.marvelapi

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MarvelApplication : Application() {

    companion object {
        lateinit var instance: MarvelApplication private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}