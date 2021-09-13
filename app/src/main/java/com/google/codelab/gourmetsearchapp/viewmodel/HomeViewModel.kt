package com.google.codelab.gourmetsearchapp.viewmodel

import androidx.databinding.ObservableBoolean
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.usecase.HomeUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val usecase: HomeUsecase
) : BaseViewModel(usecase) {
    private var currentPage = 1
    val storeList: PublishSubject<StoresBusinessModel> = PublishSubject.create()
    val hasLocation: PublishSubject<Boolean> = PublishSubject.create()
    val moreLoad = ObservableBoolean(true)

    fun fetchStores(
        range: Int = 3,
        coupon: Int = 0,
        drink: Int = 0,
        room: Int = 0,
        wifi: Int = 0,
        lunch: Int = 0
    ) {
        usecase.fetchNearStores(range, coupon, drink, room, wifi, lunch, currentPage)
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

    fun checkLocationPermission() {
        usecase.hasLocationPermission()
            .execute(
                onSuccess = { hasLocation.onNext(it) },
                retry = { checkLocationPermission() }
            )
    }
}
