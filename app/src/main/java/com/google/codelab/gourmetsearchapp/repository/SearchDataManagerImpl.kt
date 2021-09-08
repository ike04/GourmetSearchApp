package com.google.codelab.gourmetsearchapp.repository

import com.google.codelab.gourmetsearchapp.data.RemoteData
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresMapper
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchDataManagerImpl @Inject constructor(
    private val remote: RemoteData
) : SearchDataManager {

    override fun fetchNearStores(
        lat: Double,
        lng: Double,
        startPage: Int
    ): Single<StoresBusinessModel> {
        return remote.fetchStores(lat, lng, startPage)
            .map { StoresMapper.transform(it) }
    }
}
