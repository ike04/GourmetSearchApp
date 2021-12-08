package com.google.codelab.gourmetsearchapp.usecase

import com.google.android.gms.maps.model.LatLng
import com.google.codelab.gourmetsearchapp.model.FilterDataModel
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface MapsUsecase : Usecase {
    fun fetchNearStores(startPage: Int = 1): Single<StoresBusinessModel>

    fun saveLocation(latLng: LatLng)

    fun fetchFilterData()

    fun getFilterDataStream(): Observable<FilterDataModel>
}
