package com.google.codelab.gourmetsearchapp.data

import com.google.codelab.gourmetsearchapp.BuildConfig
import com.google.codelab.gourmetsearchapp.model.response.StoresResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import javax.inject.Inject

class RemoteData @Inject constructor(
    private val api: ApiRequest
) {
    companion object {
        private const val FORMAT = "json"
        private const val COUNT = 20
        private const val RANGE = 3
    }

    fun fetchStores(
        lat: Double,
        lng: Double,
        start: Int = 1
    ): Single<Response<StoresResponse>> {
        return api.fetchNearStores(
            BuildConfig.API_KEY, COUNT, lat, lng, start, RANGE, FORMAT
        )
    }
}
