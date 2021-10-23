package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.Signal
import com.google.codelab.gourmetsearchapp.repository.FavoriteDataManager
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class StoreWebViewUsecaseImpl @Inject constructor(
    private val repository: FavoriteDataManager
) : BaseUsecase(Schedulers.trampoline(), Schedulers.trampoline()), StoreWebViewUsecase {
    override fun addFavoriteStore(storeId: String) {
        repository.addFavoriteStoreId(storeId)
    }

    override fun deleteFavoriteStore(storeId: String) {
        repository.deleteFavoriteStoreId(storeId)
    }

    override fun hasFavoriteStore(storeId: String) {
        repository.hasFavoriteStoreId(storeId)
    }

    override fun getHasStoreIdStream(): Observable<Boolean> = repository.getHasStoreIdStream()

    override fun getAddIdStream(): Observable<Signal> =repository.getAddIdStream()

    override fun getDeleteIdStream(): Observable<Signal> = repository.getDeleteIdStream()
}
