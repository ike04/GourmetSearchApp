package com.google.codelab.gourmetsearchapp.repository

import com.google.android.gms.maps.model.LatLng
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface SearchDataManager {
    fun fetchNearStores(startPage: Int): Single<StoresBusinessModel>

    fun saveLocation(latLng: LatLng)

    fun getLocationStream(): Observable<LatLng>
}
