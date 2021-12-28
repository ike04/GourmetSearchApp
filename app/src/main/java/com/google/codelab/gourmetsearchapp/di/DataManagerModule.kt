package com.google.codelab.gourmetsearchapp.di

import android.app.Application
import com.google.codelab.gourmetsearchapp.repository.LocationDataManager
import com.google.codelab.gourmetsearchapp.repository.LocationDataManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataManagerModule {
    @Singleton
    @Provides
    fun provideLocationDataManager(
        application: Application
    ): LocationDataManager {
        return LocationDataManagerImpl(application.applicationContext)
    }
}
