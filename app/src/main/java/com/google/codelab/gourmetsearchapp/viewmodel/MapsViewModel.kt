package com.google.codelab.gourmetsearchapp.viewmodel

import android.annotation.SuppressLint
import android.os.Looper
import androidx.databinding.ObservableBoolean
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.google.codelab.gourmetsearchapp.Signal
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.usecase.MapsUsecase
import com.google.codelab.gourmetsearchapp.view.map.SearchFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val usecase: MapsUsecase
) : BaseViewModel(usecase, Schedulers.trampoline(), Schedulers.trampoline()) {
    private var locationCallback: LocationCallback? = null
    val storeList: PublishSubject<StoresBusinessModel> = PublishSubject.create()
    val latLng: BehaviorSubject<LatLng> = BehaviorSubject.create()
    val reset: PublishSubject<Signal> = PublishSubject.create()
    val zoom: PublishSubject<Float> = PublishSubject.create()
    val showViewPager = ObservableBoolean(false)

    fun setup() {
        usecase.getNearStores()
            .subscribeBy {
                if (it.store.isNotEmpty()) {
                    showViewPager.set(true)
                }
                storeList.onNext(it)
            }.addTo(disposables)
    }

    fun getLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        usecase.getLocation(fusedLocationProviderClient)

        usecase.getLocationStream()
            .doOnNext { saveLocation(it) }
            .subscribeBy { latLng.onNext(it) }
            .addTo(disposables)
    }

    fun stopLocationUpdates(fusedLocationProviderClient: FusedLocationProviderClient) {
        locationCallback?.let { fusedLocationProviderClient.removeLocationUpdates(it) }
    }

    fun fetchNearStores() {
        usecase.fetchNearStores()
    }

    fun saveLocation(latLng: LatLng) {
        usecase.saveLocation(latLng)
    }

    fun resetStores() {
        reset.onNext(Signal)
    }

    fun fetchFilterData() {
        usecase.fetchFilterData()

        usecase.getFilterDataStream()
            .firstElement()
            .subscribeBy {
                zoom.onNext(SearchFilter.getId(it.searchRange).zoom)
                fetchNearStores()
            }
            .addTo(disposables)
    }

}
