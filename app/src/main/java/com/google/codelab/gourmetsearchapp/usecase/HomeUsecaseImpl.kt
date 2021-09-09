package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.repository.SearchDataManager
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class HomeUsecaseImpl @Inject constructor(
    private val repository: SearchDataManager
) : BaseUsecase(), HomeUsecase {

    override fun fetchNearStores(
        range: Int,
        coupon: Int,
        drink: Int,
        wifi: Int,
        lunch: Int,
        startPage: Int
    ): Single<StoresBusinessModel> {
        return repository.fetchNearStores(
            startPage,
            range,
            coupon,
            drink,
            wifi,
            lunch
        )
    }
}
