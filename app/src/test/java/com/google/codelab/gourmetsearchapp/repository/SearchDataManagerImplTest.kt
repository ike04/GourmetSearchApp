package com.google.codelab.gourmetsearchapp.repository

import com.google.android.gms.maps.model.LatLng
import com.google.codelab.gourmetsearchapp.data.LocalData
import com.google.codelab.gourmetsearchapp.data.RemoteData
import com.google.codelab.gourmetsearchapp.model.FilterDataModel
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.model.response.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given
import retrofit2.Response

class SearchDataManagerImplTest {
    private lateinit var sut: SearchDataManager

    @Mock
    private lateinit var remote: RemoteData

    @Mock
    private lateinit var local: LocalData

    companion object {
        private val aLatLng = LatLng(10.0,10.0)
        private val aFilterData = FilterDataModel(searchRange = 3, genre = "", coupon = 0, drink = 0, privateRoom = 0, wifi = 0, lunch = 0, keyword = "")
        private val aStoreList = Store(id = "J999999999", name = "レストラン", logo= "", lat = 10.0, lng =  10.0, genre = Genre("イタリアン"), budget = Budget("2000円", "2000円"),urls = Urls(""),photo = Photos(photo = Photo("")))
        private val aStoresResponse = StoresResponse(results = Results(apiVersion = "1.0.0", totalPages = 1, getPages = 1 ,store = listOf(aStoreList)))
        private val aStoresBusinessModel = StoresBusinessModel(totalPages = 1, getPages = 1, store = listOf(com.google.codelab.gourmetsearchapp.model.businessmodel.Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = "")))
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        sut = SearchDataManagerImpl(remote, local)
    }

    @Test
    fun testFetchNearStore_hasFilterData() {
        given(remote.fetchStores(aLatLng.latitude, aLatLng.longitude, aFilterData.searchRange,
            aFilterData.genre, aFilterData.coupon, aFilterData.drink, aFilterData.privateRoom,
            aFilterData.wifi, aFilterData.lunch, aFilterData.keyword,1)).willReturn(Single.just(Response.success(aStoresResponse)))

        sut.saveFilterData(aFilterData)
        sut.saveLocation(aLatLng)
        val testObserver = sut.fetchNearStores(1).test()

        testObserver.assertValue(aStoresBusinessModel).assertNoErrors()
    }

    @Test
    fun testFetchNearStore_hasNoFilterData() {
        given(remote.fetchStores(aLatLng.latitude, aLatLng.longitude, aFilterData.searchRange,
            aFilterData.genre, aFilterData.coupon, aFilterData.drink, aFilterData.privateRoom,
            aFilterData.wifi, aFilterData.lunch, aFilterData.keyword,1)).willReturn(Single.just(Response.success(aStoresResponse)))

        sut.saveLocation(aLatLng)
        val testObserver = sut.fetchNearStores(1).test()

        testObserver.assertValue(aStoresBusinessModel).assertNoErrors()
    }

    @Test
    fun testHasLocationPermission() {
        sut.saveLocation(aLatLng)
        val testObserver = sut.hasLocationPermission().test()

        testObserver.assertValue(true).assertNoErrors()
    }

    @Test
    fun testGetFilterDataStream() {
        given(local.getFilterDataStream()).willReturn(Observable.just(aFilterData))

        val testObserver = sut.getFilterDataStream().test()

        testObserver.assertValue(aFilterData).assertNoErrors()
    }

}
