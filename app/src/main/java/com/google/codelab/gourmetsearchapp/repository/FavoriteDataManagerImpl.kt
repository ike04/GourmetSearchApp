package com.google.codelab.gourmetsearchapp.repository

import com.google.codelab.gourmetsearchapp.Signal
import com.google.codelab.gourmetsearchapp.data.LocalData
import com.google.codelab.gourmetsearchapp.data.LocalStoreIdData
import com.google.codelab.gourmetsearchapp.data.RemoteData
import com.google.codelab.gourmetsearchapp.model.businessmodel.Store
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresMapper
import com.google.codelab.gourmetsearchapp.model.local.FavoriteStoreEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FavoriteDataManagerImpl @Inject constructor(
    private val remote: RemoteData,
    private val local: LocalStoreIdData
) : FavoriteDataManager {
    override fun fetchStoreIds(): Single<List<String>> {
        return local.fetchStoreIds()
    }

    override fun fetchFavoriteStores(ids: String): Single<StoresBusinessModel> {
        return remote.fetchFavoriteStores(ids)
            .map { StoresMapper.transform(it) }
    }

    override fun addFavoriteStoreId(storeId: String) {
        local.addStoreId(storeId)
    }

    override fun deleteFavoriteStoreId(storeId: String) {
        local.deleteStoreId(storeId)
    }

    override fun hasFavoriteStoreId(storeId: String) {
        local.hasStoreId(storeId)
    }

    override fun getStoreIdsStream(): Observable<List<String>> = local.getStoreIdsStream()

    override fun getHasStoreIdStream(): Observable<Boolean> = local.getHasStoreIdStream()

    override fun getAddIdStream(): Observable<Signal> = local.getAddIdStream()

    override fun getDeleteIdStream(): Observable<Signal> = local.getDeleteIdStream()
}
