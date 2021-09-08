package com.google.codelab.gourmetsearchapp.repository

import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import io.reactivex.rxjava3.core.Single

interface SearchDataManager {
    fun fetchNearStores(lat: Double, lng: Double, startPage: Int): Single<StoresBusinessModel>
}
