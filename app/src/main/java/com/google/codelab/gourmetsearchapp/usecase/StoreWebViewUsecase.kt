package com.google.codelab.gourmetsearchapp.usecase

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface StoreWebViewUsecase: Usecase {
    fun addFavoriteStore(storeId: String): Completable

    fun deleteFavoriteStore(storeId: String): Completable

    fun hasFavoriteStore(storeId: String): Single<Boolean>
}
