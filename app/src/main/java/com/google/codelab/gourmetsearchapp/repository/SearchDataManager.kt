package com.google.codelab.gourmetsearchapp.repository

import com.google.android.gms.maps.model.LatLng
import com.google.codelab.gourmetsearchapp.model.FilterDataModel
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface SearchDataManager {
    fun fetchNearStores(
        range: Int,
        coupon: Int,
        drink: Int,
        room: Int,
        wifi: Int,
        lunch: Int,
        startPage: Int
    ): Single<StoresBusinessModel>

    fun saveLocation(latLng: LatLng)

    fun hasLocationPermission(): Single<Boolean>

    fun getLocationStream(): Observable<LatLng>

    fun saveFilterData(filterData: FilterDataModel)

    fun fetchFilterData()

    fun resetFilterData()

    fun getFilterDataStream(): Observable<FilterDataModel>
}
