package com.google.codelab.gourmetsearchapp.viewmodel

import androidx.databinding.ObservableBoolean
import com.google.android.gms.maps.model.LatLng
import com.google.codelab.gourmetsearchapp.Signal
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.usecase.MapsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val usecase: MapsUsecase
) : BaseViewModel(usecase, Schedulers.trampoline(), Schedulers.trampoline()) {
    val storeList: PublishSubject<StoresBusinessModel> = PublishSubject.create()
    val reset: PublishSubject<Signal> = PublishSubject.create()
    val showViewPager = ObservableBoolean(false)

    fun fetchNearStores() {
        usecase.fetchNearStores()
            .execute(
                onSuccess = {
                    if (it.store.isNotEmpty()) {
                        showViewPager.set(true)
                    }
                    storeList.onNext(it)
                },
                retry = { fetchNearStores() }
            )
    }

    fun saveLocation(latLng: LatLng) {
        usecase.saveLocation(latLng)
    }

    fun resetStores() {
        reset.onNext(Signal)
    }

}
