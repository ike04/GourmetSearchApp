package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.Signal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface StoreWebViewUsecase: Usecase {
    fun addFavoriteStore(storeId: String)

    fun deleteFavoriteStore(storeId: String)

    fun hasFavoriteStore(storeId: String)

    fun getHasStoreIdStream(): Observable<Boolean>

    fun getAddIdStream(): Observable<Signal>

    fun getDeleteIdStream(): Observable<Signal>
}
