package com.google.codelab.gourmetsearchapp.data

import com.google.codelab.gourmetsearchapp.model.FilterDataModel
import com.google.codelab.gourmetsearchapp.model.local.FilterEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class LocalData @Inject constructor(private val dao: FilterDao) {
    private val filterData: PublishSubject<FilterDataModel> = PublishSubject.create()

    fun saveFilterData(filterData: FilterDataModel) {
        Single.fromCallable {
            dao.saveFilterData(
                FilterEntity(
                    id = 1,
                    searchRange = filterData.searchRange,
                    genre = filterData.genre,
                    coupon = filterData.coupon,
                    drink = filterData.drink,
                    privateRoom = filterData.privateRoom,
                    wifi = filterData.wifi,
                    lunch = filterData.lunch
                )
            )
        }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun fetchFilterData() {
        Single.fromCallable { dao.loadFilterDate() }
            .subscribeOn(Schedulers.io())
            .onErrorComplete()
            .subscribeBy { localData ->
                filterData.onNext(
                    FilterDataModel(
                        searchRange = localData.searchRange,
                        genre = localData.genre,
                        coupon = localData.coupon,
                        drink = localData.drink,
                        privateRoom = localData.privateRoom,
                        wifi = localData.wifi,
                        lunch = localData.lunch
                    )
                )
            }
    }

    fun resetFilter() {
        Single.fromCallable {
            dao.deleteFilterData()
        }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun getFilterDataStream(): Observable<FilterDataModel> = filterData.hide()
}
