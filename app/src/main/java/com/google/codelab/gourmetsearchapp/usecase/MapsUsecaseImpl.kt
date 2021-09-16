package com.google.codelab.gourmetsearchapp.usecase

import com.google.android.gms.maps.model.LatLng
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.repository.SearchDataManager
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MapsUsecaseImpl @Inject constructor(
    private val repository: SearchDataManager
) : BaseUsecase(), MapsUsecase {

    override fun fetchNearStores(startPage: Int): Single<StoresBusinessModel> {
        return repository.fetchNearStores(startPage)
    }

    override fun saveLocation(latLng: LatLng) {
        repository.saveLocation(latLng)
    }
}
