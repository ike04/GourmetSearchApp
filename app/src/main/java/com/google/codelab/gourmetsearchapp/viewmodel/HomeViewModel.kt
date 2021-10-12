package com.google.codelab.gourmetsearchapp.viewmodel

import androidx.databinding.ObservableBoolean
import com.google.codelab.gourmetsearchapp.Signal
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.usecase.HomeUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val usecase: HomeUsecase
) : BaseViewModel(usecase, Schedulers.trampoline(),Schedulers.trampoline()) {
    private var currentPage = 1
    val storeList: PublishSubject<StoresBusinessModel> = PublishSubject.create()
    val hasLocation: PublishSubject<Boolean> = PublishSubject.create()
    val reset: PublishSubject<Signal> = PublishSubject.create()
    val moreLoad = ObservableBoolean(true)
    val selectedFavorite = ObservableBoolean(false)

    fun fetchStores() {
        usecase.fetchNearStores(currentPage)
            .execute(
                onSuccess = {
                    if (it.store.size < 20) {
                        moreLoad.set(false)
                    }
                    currentPage += it.totalPages
                    storeList.onNext(it)
                },
                retry = { fetchStores() }
            )
    }

    fun fetchFavoriteStores(forceUpdate: Boolean = false) {
        usecase.fetchFavoriteStores(forceUpdate)

        usecase.getFavoriteStoreStream()
            .firstElement()
            .subscribeBy {
                if (it.store.size < 20) {
                    moreLoad.set(false)
                }
                storeList.onNext(it)
            }
    }

    fun resetPages() {
        currentPage = 1
        moreLoad.set(true)
    }

    fun resetStoreList() {
        reset.onNext(Signal)
    }

    fun checkLocationPermission() {
        usecase.hasLocationPermission()
            .execute(
                onSuccess = { hasLocation.onNext(it) },
                retry = { checkLocationPermission() }
            )
    }
}
