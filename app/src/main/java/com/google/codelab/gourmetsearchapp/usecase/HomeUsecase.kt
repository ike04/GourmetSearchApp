package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import io.reactivex.rxjava3.core.Single

interface HomeUsecase : Usecase {
    fun fetchNearStores(
        range: Int,
        coupon: Int,
        drink: Int,
        room: Int,
        wifi: Int,
        lunch: Int,
        startPage: Int = 1
    ): Single<StoresBusinessModel>
}
