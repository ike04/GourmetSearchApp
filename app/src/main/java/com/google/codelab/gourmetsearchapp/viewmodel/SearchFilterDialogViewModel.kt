package com.google.codelab.gourmetsearchapp.viewmodel

import com.google.codelab.gourmetsearchapp.Signal
import com.google.codelab.gourmetsearchapp.model.FilterDataModel
import com.google.codelab.gourmetsearchapp.usecase.SearchFilterDialogUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class SearchFilterDialogViewModel @Inject constructor(
    private val usecase: SearchFilterDialogUsecase
) : BaseViewModel(usecase) {
    val onSearchClicked: PublishSubject<Signal> = PublishSubject.create()
    val onResetClicked: PublishSubject<Signal> = PublishSubject.create()
    val onCancelClicked: PublishSubject<Signal> = PublishSubject.create()
    val filterData: PublishSubject<FilterDataModel> = PublishSubject.create()

    fun saveFilterData(filterData: FilterDataModel) {
        usecase.saveFilterData(filterData)
    }

    fun fetchFilterData() {
        usecase.fetchFilterData()

        usecase.getFilterDataStream()
            .subscribeBy { filterData.onNext(it) }
            .addTo(disposables)
    }

    fun resetFilter() {
        usecase.resetFilterData()
    }

    fun onSearchClick() {
        onSearchClicked.onNext(Signal)
    }

    fun onResetClick() {
        onResetClicked.onNext(Signal)
    }

    fun onCancelClick() {
        onCancelClicked.onNext(Signal)
    }
}
