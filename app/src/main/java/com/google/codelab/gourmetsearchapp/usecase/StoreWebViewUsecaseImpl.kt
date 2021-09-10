package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.repository.FavoriteDataManager
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class StoreWebViewUsecaseImpl @Inject constructor(
    private val repository: FavoriteDataManager
) : BaseUsecase(), StoreWebViewUsecase {
    override fun addFavoriteStore(storeId: String): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFavoriteStore(storeId: String): Completable {
        TODO("Not yet implemented")
    }

    override fun hasFavoriteStore(storeId: String): Single<Boolean> {
        TODO("Not yet implemented")
    }
}
