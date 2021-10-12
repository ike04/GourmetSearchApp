package com.google.codelab.gourmetsearchapp.viewmodel

import androidx.databinding.ObservableBoolean
import com.google.codelab.gourmetsearchapp.Signal
import com.google.codelab.gourmetsearchapp.model.FilterDataModel
import com.google.codelab.gourmetsearchapp.usecase.SearchFilterDialogUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class SearchFilterDialogViewModel @Inject constructor(
    private val usecase: SearchFilterDialogUsecase
) : BaseViewModel(usecase, Schedulers.trampoline(), Schedulers.trampoline()) {
    val onSearchClicked: PublishSubject<Signal> = PublishSubject.create()
    val onResetClicked: PublishSubject<Signal> = PublishSubject.create()
    val onCancelClicked: PublishSubject<Signal> = PublishSubject.create()
    val filterData: PublishSubject<FilterDataModel> = PublishSubject.create()
    val hasLocation = ObservableBoolean(false)

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

    fun hasLocationPermission() {
        usecase.getHasLocationPermissionStream()
            .subscribeBy { hasLocation.set(it) }
            .addTo(disposables)
    }
}
