package com.google.codelab.gourmetsearchapp.usecase

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.google.codelab.gourmetsearchapp.model.FilterDataModel
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface MapsUsecase : Usecase {
    fun fetchNearStores(startPage: Int = 1)

    fun getNearStores(): Observable<StoresBusinessModel>

    fun saveLocation(latLng: LatLng)

    fun fetchFilterData()

    fun getFilterDataStream(): Observable<FilterDataModel>

    fun getLocation(fusedLocationProviderClient: FusedLocationProviderClient)

    fun getLocationStream(): Observable<LatLng>
}
