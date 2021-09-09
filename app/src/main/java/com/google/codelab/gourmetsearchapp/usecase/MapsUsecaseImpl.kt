package com.google.codelab.gourmetsearchapp.usecase

import com.google.android.gms.maps.model.LatLng
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.repository.SearchDataManager
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MapsUsecaseImpl @Inject constructor(
    private val repository: SearchDataManager
) : BaseUsecase(), MapsUsecase {

    override fun fetchNearStores(
        range: Int,
        coupon: Int,
        drink: Int,
        room: Int,
        wifi: Int,
        lunch: Int,
        startPage: Int
    ): Single<StoresBusinessModel> {
        return repository.fetchNearStores(
            range,
            coupon,
            drink,
            room,
            wifi,
            lunch,
            startPage
        )
    }

    override fun saveLocation(latLng: LatLng) {
        repository.saveLocation(latLng)
    }
}
