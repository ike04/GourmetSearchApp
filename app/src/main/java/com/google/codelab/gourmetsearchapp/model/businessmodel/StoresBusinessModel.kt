package com.google.codelab.gourmetsearchapp.model.businessmodel

data class StoresBusinessModel(
    val totalPages: Int,
    val getPages: Int,
    val store: List<Store>
)

data class Store(
    val id: String,
    val name: String,
    val lat: Double,
    val lng: Double,
    val genre: String,
    val budget: String,
    val urls: String,
    val photo: String
)
