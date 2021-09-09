package com.google.codelab.gourmetsearchapp.repository

import com.google.android.gms.maps.model.LatLng
import com.google.codelab.gourmetsearchapp.data.RemoteData
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresMapper
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class SearchDataManagerImpl @Inject constructor(
    private val remote: RemoteData
) : SearchDataManager {
    private val latLng: BehaviorSubject<LatLng> = BehaviorSubject.create()

    override fun fetchNearStores(
        range: Int,
        coupon: Int,
        drink: Int,
        room: Int,
        wifi: Int,
        lunch: Int,
        startPage: Int
    ): Single<StoresBusinessModel> {
        return remote.fetchStores(
            latLng.value.latitude,
            latLng.value.longitude,
            range,
            coupon,
            drink,
            room,
            wifi,
            lunch,
            startPage
        ).map { StoresMapper.transform(it) }
    }

    override fun saveLocation(latLng: LatLng) {
        this.latLng.onNext(latLng)
    }

    override fun getLocationStream(): Observable<LatLng> = latLng.hide()
}
