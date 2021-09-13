package com.google.codelab.gourmetsearchapp.repository

import com.google.codelab.gourmetsearchapp.Signal
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface FavoriteDataManager {
    fun fetchStoreIds()

    fun addFavoriteStoreId(storeId: String)

    fun deleteFavoriteStoreId(storeId: String)

    fun hasFavoriteStoreId(storeId: String)

    fun getHasStoreIdStream(): Observable<Boolean>

    fun getAddIdStream(): Observable<Signal>

    fun getDeleteIdStream(): Observable<Signal>
}
