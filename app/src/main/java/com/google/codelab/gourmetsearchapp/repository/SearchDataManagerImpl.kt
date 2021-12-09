package com.google.codelab.gourmetsearchapp.repository

import com.google.android.gms.maps.model.LatLng
import com.google.codelab.gourmetsearchapp.data.LocalData
import com.google.codelab.gourmetsearchapp.data.RemoteData
import com.google.codelab.gourmetsearchapp.model.FilterDataModel
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresMapper
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class SearchDataManagerImpl @Inject constructor(
    private val remote: RemoteData,
    private val local: LocalData
) : SearchDataManager {
    private val latLng: BehaviorSubject<LatLng> = BehaviorSubject.create()
    private val filterData: BehaviorSubject<FilterDataModel> = BehaviorSubject.create()

    override fun fetchNearStores(startPage: Int): Single<StoresBusinessModel> {
        return if (filterData.hasValue()) {
            remote.fetchStores(
                latLng.value!!.latitude,
                latLng.value!!.longitude,
                filterData.value!!.searchRange,
                filterData.value!!.genre,
                filterData.value!!.coupon,
                filterData.value!!.drink,
                filterData.value!!.privateRoom,
                filterData.value!!.wifi,
                filterData.value!!.lunch,
                filterData.value!!.keyword,
                startPage
            ).map { StoresMapper.transform(it) }
        } else {
            remote.fetchStores(
                latLng.value!!.latitude,
                latLng.value!!.longitude,
                3,
                "",
                0,
                0,
                0,
                0,
                0,
                "",
                startPage
            ).map { StoresMapper.transform(it) }
        }
    }

    override fun saveLocation(latLng: LatLng) {
        this.latLng.onNext(latLng)
    }

    override fun hasLocationPermission(): Single<Boolean> = Single.fromCallable { latLng.hasValue() }

    override fun getLocationStream(): Observable<LatLng> = latLng.hide()

    override fun saveFilterData(filterData: FilterDataModel) {
        local.saveFilterData(filterData)
        this.filterData.onNext(filterData)
    }

    override fun fetchFilterData() {
        local.fetchFilterData()
    }

    override fun resetFilterData() {
        local.resetFilter()
    }

    override fun getFilterDataStream(): Observable<FilterDataModel> {
        return local.getFilterDataStream()
            .doOnNext { filterData.onNext(it) }
    }
}
