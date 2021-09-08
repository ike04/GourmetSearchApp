package com.google.codelab.gourmetsearchapp.viewmodel

import com.google.android.gms.maps.model.LatLng
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.usecase.MapsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val usecase: MapsUsecase
) : BaseViewModel(usecase) {
    val storeList: PublishSubject<StoresBusinessModel> = PublishSubject.create()

    fun fetchNearStores() {
        usecase.fetchNearStores()
            .execute(
                onSuccess = { storeList.onNext(it) },
                retry = { fetchNearStores() }
            )
    }

    fun saveLocation(latLng: LatLng) {
        usecase.saveLocation(latLng)
    }

}
