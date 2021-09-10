package com.google.codelab.gourmetsearchapp.usecase

import com.google.android.gms.maps.model.LatLng
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import io.reactivex.rxjava3.core.Single

interface MapsUsecase : Usecase {
    fun fetchNearStores(
        range: Int,
        coupon: Int,
        drink: Int,
        room: Int,
        wifi: Int,
        lunch: Int,
        startPage: Int = 1
    ): Single<StoresBusinessModel>

    fun saveLocation(latLng: LatLng)
}
