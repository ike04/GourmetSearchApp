package com.google.codelab.gourmetsearchapp.data

import com.google.codelab.gourmetsearchapp.Signal
import com.google.codelab.gourmetsearchapp.model.local.FavoriteStoreEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class LocalStoreIdData @Inject constructor(private val dao: FavoriteStoreDao) {
    private val storeIds: PublishSubject<List<String>> = PublishSubject.create()
    private val hasStoreId: PublishSubject<Boolean> = PublishSubject.create()
    private val addId: PublishSubject<Signal> = PublishSubject.create()
    private val deleteId: PublishSubject<Signal> = PublishSubject.create()

    fun fetchStoreIds(): Single<List<String>> {
        return Single.fromCallable { dao.loadFavoriteIds() }
            .subscribeOn(Schedulers.io())
            .onErrorReturn { emptyList() }
            .map { it.map { it.storeId } }
    }

    fun addStoreId(storeId: String) {
        Single.fromCallable { dao.saveFavoriteStoreId(FavoriteStoreEntity(storeId)) }
            .subscribeOn(Schedulers.io())
            .subscribeBy {
                addId.onNext(Signal)
            }
    }

    fun deleteStoreId(storeId: String) {
        Single.fromCallable { dao.deleteStoreId(storeId) }
            .subscribeOn(Schedulers.io())
            .subscribeBy {
                deleteId.onNext(Signal)
            }
    }

    fun hasStoreId(storeId: String) {
        Single.fromCallable { dao.hasStoreId(storeId) }
            .subscribeOn(Schedulers.io())
            .subscribeBy {
                hasStoreId.onNext(it.isNotEmpty())
            }
    }

    fun getStoreIdsStream(): Observable<List<String>> = storeIds.hide()

    fun getHasStoreIdStream(): Observable<Boolean> = hasStoreId.hide()

    fun getAddIdStream(): Observable<Signal> = addId.hide()

    fun getDeleteIdStream(): Observable<Signal> = deleteId.hide()
}
