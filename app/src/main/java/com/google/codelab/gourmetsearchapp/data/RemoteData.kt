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
    }

    fun fetchStores(
        lat: Double,
        lng: Double,
        range: Int,
        genre: String,
        coupon: Int,
        drink: Int,
        room: Int,
        wifi: Int,
        lunch: Int,
        keyword: String,
        start: Int = 1
    ): Single<Response<StoresResponse>> {
        return api.fetchNearStores(
           "",
            COUNT,
            lat,
            lng,
            start,
            range,
            genre,
            coupon,
            drink,
            room,
            wifi,
            lunch,
            keyword,
            FORMAT
        )
    }

    fun fetchFavoriteStores(ids: String): Single<Response<StoresResponse>> {
        return api.fetchFavoriteStores("", COUNT, ids, FORMAT)
    }
}
