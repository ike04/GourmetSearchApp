package com.google.codelab.gourmetsearchapp.di

import com.google.codelab.gourmetsearchapp.repository.SearchDataManager
import com.google.codelab.gourmetsearchapp.repository.SearchDataManagerImpl
import com.google.codelab.gourmetsearchapp.usecase.MapsUsecase
import com.google.codelab.gourmetsearchapp.usecase.MapsUsecaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindSearchDataManager(searchDataManagerImpl: SearchDataManagerImpl): SearchDataManager

    @Singleton
    @Binds
    abstract fun bindMapsUsecase(mapsUsecaseImpl: MapsUsecaseImpl): MapsUsecase
}
