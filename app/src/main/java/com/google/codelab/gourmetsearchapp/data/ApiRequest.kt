package com.google.codelab.gourmetsearchapp.data

import com.google.codelab.gourmetsearchapp.model.response.StoresResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {
    @GET(".")
    fun fetchNearStores(
        @Query("key") key: String,
        @Query("count") count: Int,
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("start") start: Int,
        @Query("range") range: Int,
        @Query("format") format: String
    ): Single<Response<StoresResponse>>
}
