package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.model.FilterDataModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface SearchFilterDialogUsecase: Usecase {
    fun saveFilterData(filterData: FilterDataModel)

    fun fetchFilterData()

    fun resetFilterData()

    fun getFilterDataStream(): Observable<FilterDataModel>
}
