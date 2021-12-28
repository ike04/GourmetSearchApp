package com.google.codelab.gourmetsearchapp.repository

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface LocationDataManager {
    fun fetchLocation(fusedLocationProviderClient: FusedLocationProviderClient): Completable

    fun getLocationStream(): Observable<LatLng>
}
