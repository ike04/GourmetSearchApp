package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.model.FilterDataModel
import com.google.codelab.gourmetsearchapp.repository.SearchDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchFilterDialogUsecaseImpl @Inject constructor(
    private val repository: SearchDataManager
) : BaseUsecase(), SearchFilterDialogUsecase {

    override fun saveFilterData(filterData: FilterDataModel) {
        repository.saveFilterData(filterData)
    }

    override fun fetchFilterData() {
        repository.fetchFilterData()
    }

    override fun getFilterDataStream(): Observable<FilterDataModel> = repository.getFilterDataStream()
}
