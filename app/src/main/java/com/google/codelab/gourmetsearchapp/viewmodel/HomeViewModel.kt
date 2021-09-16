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

    fun resetPages() {
        currentPage = 1
        moreLoad.set(true)
    }

    fun checkLocationPermission() {
        usecase.hasLocationPermission()
            .execute(
                onSuccess = { hasLocation.onNext(it) },
                retry = { checkLocationPermission() }
            )
    }
}
