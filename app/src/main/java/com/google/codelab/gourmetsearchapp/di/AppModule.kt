package com.google.codelab.gourmetsearchapp.di

import com.google.codelab.gourmetsearchapp.repository.FavoriteDataManager
import com.google.codelab.gourmetsearchapp.repository.FavoriteDataManagerImpl
import com.google.codelab.gourmetsearchapp.repository.SearchDataManager
import com.google.codelab.gourmetsearchapp.repository.SearchDataManagerImpl
import com.google.codelab.gourmetsearchapp.usecase.*
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
    abstract fun bindFavoriteDataManager(favoriteDataManagerImpl: FavoriteDataManagerImpl): FavoriteDataManager

    @Singleton
    @Binds
    abstract fun bindBaseUsecase(baseUsecase: BaseUsecase): Usecase

    @Singleton
    @Binds
    abstract fun bindHomeUsecase(homeUsecaseImpl: HomeUsecaseImpl): HomeUsecase

    @Singleton
    @Binds
    abstract fun bindMapsUsecase(mapsUsecaseImpl: MapsUsecaseImpl): MapsUsecase

    @Singleton
    @Binds
    abstract fun bindFavoriteStoresUsecase(favoriteStoresUsecaseImpl: FavoriteStoresUsecaseImpl): FavoriteStoresUsecase

    @Singleton
    @Binds
    abstract fun bindStoreWebViewUsecase(storeWebViewUsecaseImpl: StoreWebViewUsecaseImpl): StoreWebViewUsecase

    @Singleton
    @Binds
    abstract fun bindOnboardingUsecase(onboardingUsecaseImpl: OnboardingUsecaseImpl): OnboardingUsecase

    @Singleton
    @Binds
    abstract fun bindSearchFilterDialogUsecase(searchFilterDialogUsecaseImpl: SearchFilterDialogUsecaseImpl): SearchFilterDialogUsecase
}
