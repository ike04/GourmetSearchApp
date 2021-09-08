package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import io.reactivex.rxjava3.core.Single

interface HomeUsecase: Usecase {
    fun fetchNearStores(startPage: Int = 1): Single<StoresBusinessModel>
}
