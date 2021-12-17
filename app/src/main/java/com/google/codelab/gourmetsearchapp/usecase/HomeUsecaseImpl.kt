package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.model.Failure
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.repository.FavoriteDataManager
import com.google.codelab.gourmetsearchapp.repository.SearchDataManager
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class HomeUsecaseImpl @Inject constructor(
    private val repository: SearchDataManager,
    private val favoriteRepository: FavoriteDataManager
) : BaseUsecase(Schedulers.trampoline(), Schedulers.trampoline()), HomeUsecase {
    private val favoriteStoreList: PublishSubject<StoresBusinessModel> = PublishSubject.create()
    private val favoriteIds: MutableList<String> = ArrayList()
    private var currentStoresCount: Int = 0

    companion object {
        private const val LIMIT = 20
    }

    override fun fetchNearStores(startPage: Int): Single<StoresBusinessModel> {
        return repository.fetchNearStores(startPage)
    }

    override fun fetchFavoriteStores(forceUpdate: Boolean) {
        if (forceUpdate) {
            currentStoresCount = 0
            favoriteIds.clear()
        }
        if (favoriteIds.isEmpty()) {
            favoriteRepository.fetchStoreIds()
                .subscribeBy { storeIds ->
                    if (storeIds.isEmpty()) {
                        // TODO
                    }
                    favoriteIds.addAll(storeIds)
                    fetchStores(createStoreIdQuery(storeIds))
                }.addTo(disposables)
        } else {
            fetchStores(createStoreIdQuery(favoriteIds))
        }
    }

    private fun createStoreIdQuery(storeIds: List<String>): String {
        if (storeIds.isEmpty()) return ""
        val nextListCount = if (storeIds.size - currentStoresCount > LIMIT ) {
            LIMIT
        } else {
            storeIds.size - currentStoresCount
        }

        // queryに含められるstore_idが20件までのため
        return storeIds.subList(currentStoresCount, currentStoresCount + nextListCount).joinToString(",")
    }

    private fun fetchStores(ids: String) {
        favoriteRepository.fetchFavoriteStores(ids)
            .execute(
                onSuccess = { model ->
                    favoriteStoreList.onNext(model)
                    currentStoresCount = model.getPages
                },
                retry = { fetchStores(ids) }
            )
    }

    override fun hasLocationPermission(): Single<Boolean> {
        return repository.hasLocationPermission()
    }

    override fun getFavoriteStoreStream(): Observable<StoresBusinessModel> =
        favoriteStoreList.hide()

    override fun getStoreIdsStream(): Observable<List<String>> =
        favoriteRepository.getStoreIdsStream()

    override fun errorSignal(): Observable<Failure> = error.hide()

}
