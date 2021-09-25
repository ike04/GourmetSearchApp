package com.google.codelab.gourmetsearchapp.repository

import com.google.codelab.gourmetsearchapp.Signal
import com.google.codelab.gourmetsearchapp.model.businessmodel.Store
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.model.local.FavoriteStoreEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface FavoriteDataManager {
    fun fetchStoreIds(): Single<List<String>>

    fun fetchFavoriteStores(ids: String): Single<StoresBusinessModel>

    fun addFavoriteStoreId(storeId: String)

    fun deleteFavoriteStoreId(storeId: String)

    fun hasFavoriteStoreId(storeId: String)

    fun getStoreIdsStream(): Observable<List<String>>

    fun getHasStoreIdStream(): Observable<Boolean>

    fun getAddIdStream(): Observable<Signal>

    fun getDeleteIdStream(): Observable<Signal>
}
