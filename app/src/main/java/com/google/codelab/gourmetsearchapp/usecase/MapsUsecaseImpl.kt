package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.repository.SearchDataManager
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MapsUsecaseImpl @Inject constructor(
    private val repository: SearchDataManager
) : BaseUsecase(), MapsUsecase {

    override fun fetchNearStores(
        lat: Double,
        lng: Double,
        startPage: Int
    ): Single<StoresBusinessModel> {
        return repository.fetchNearStores(lat, lng, startPage)
    }
}
