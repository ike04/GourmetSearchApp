package com.google.codelab.gourmetsearchapp.model.response

import com.google.gson.annotations.SerializedName

data class StoresResponse(
    val results: Results,
)

data class Results(
    @SerializedName("api_version")
    val apiVersion: String,
    @SerializedName("results_returned")
    val totalPages: Int,
    @SerializedName("shop")
    val store: List<Store>
)

data class Store(
    val id: String,
    val name: String,
    @SerializedName("logo_image")
    val logo: String,
    val lat: Double,
    val lng: Double,
    val genre: Genre,
    val budget: Budget,
    val urls: Urls,
    val photo: Photos
)

data class Genre(
    val name : String
)

data class Budget(
    val name: String,
    val average: String
)

data class Urls(
    @SerializedName("pc")
    val url: String
)

data class Photos(
    @SerializedName("pc")
    val photo: Photo
)

data class Photo(
    @SerializedName("m")
    val logo: String
)
