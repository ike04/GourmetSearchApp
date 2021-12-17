package com.google.codelab.gourmetsearchapp.usecase

import com.google.android.gms.maps.model.LatLng
import com.google.codelab.gourmetsearchapp.model.FilterDataModel
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.repository.FavoriteDataManager
import com.google.codelab.gourmetsearchapp.repository.SearchDataManager
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class MapsUsecaseImpl @Inject constructor(
    private val repository: SearchDataManager,
    private val favoriteRepository: FavoriteDataManager
) : BaseUsecase(Schedulers.trampoline(), Schedulers.trampoline()), MapsUsecase {
    private val stores: PublishSubject<StoresBusinessModel> = PublishSubject.create()

    override fun fetchNearStores(startPage: Int) {
        Observables.combineLatest(
            repository.fetchNearStores(startPage).toObservable(),
            favoriteRepository.fetchStoreIds().toObservable()
        ).doOnNext { (remote, local) ->
            val store = remote.store.map { store ->
                val hasFavorite = local.contains(store.id)
                store.copy(isFavorite = hasFavorite)
            }
            val latestStores = remote.copy(store = store)
            stores.onNext(latestStores)
        }
            .subscribeBy()
            .addTo(disposables)
    }

    override fun getNearStores(): Observable<StoresBusinessModel> = stores.hide()

    override fun saveLocation(latLng: LatLng) {
        repository.saveLocation(latLng)
    }

    override fun fetchFilterData() {
        repository.fetchFilterData()
    }

    override fun getFilterDataStream(): Observable<FilterDataModel> = repository.getFilterDataStream()
}
