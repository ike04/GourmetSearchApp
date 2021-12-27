package com.google.codelab.gourmetsearchapp.viewmodel

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.google.codelab.gourmetsearchapp.Signal
import com.google.codelab.gourmetsearchapp.model.Failure
import com.google.codelab.gourmetsearchapp.model.FilterDataModel
import com.google.codelab.gourmetsearchapp.model.businessmodel.Store
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.usecase.MapsUsecase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

class MapsViewModelTest {
    private lateinit var sut: MapsViewModel

    private lateinit var anErrorSubject: PublishSubject<Failure>

    @Mock
    private lateinit var usecase: MapsUsecase

    @Mock
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    companion object {
        private val aLocationRequest = LocationRequest.create()
        private val aLatLng = LatLng(10.0,10.0)
        private val aStoresBusinessModel = StoresBusinessModel(totalPages = 1, getPages = 1, store = listOf(Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = "", isFavorite = false)))
        private val aNoStoresBusinessModel = StoresBusinessModel(totalPages = 0, getPages = 0, store = emptyList())
        private val aFilterDataModel = FilterDataModel(searchRange = 1, genre = "イタリアン", coupon = 0, drink = 0, privateRoom = 0, wifi = 0, lunch = 0, keyword = "")

    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        anErrorSubject = PublishSubject.create()
        given(usecase.errorSignal()).willReturn(anErrorSubject)
        sut = MapsViewModel(usecase)
    }

//    @Test
//    fun testGetLocation() {
//        val testObserver = sut.latLng.test()
//
//        sut.getLocation(aLocationRequest, fusedLocationProviderClient)
//
//        testObserver.assertValue(aLatLng).assertNoErrors()
//    }

    @Test
    fun testStopLocationUpdates() {
        sut.stopLocationUpdates(fusedLocationProviderClient)
    }

    @Test
    fun testFetchNearStores() {
        given(usecase.getNearStores()).willReturn(Observable.just(aStoresBusinessModel))
        val testObserver = sut.storeList.test()
        val showViewPagerObserver = sut.showViewPager

        sut.setup()
        sut.fetchNearStores()

        testObserver.assertValue(aStoresBusinessModel).assertValueCount(1).assertNoErrors()
        assertTrue(showViewPagerObserver.get())
    }

    @Test
    fun testFetchNearStores_NoStores() {
        given(usecase.getNearStores()).willReturn(Observable.just(aNoStoresBusinessModel))
        val testObserver = sut.storeList.test()
        val showViewPagerObserver = sut.showViewPager

        sut.setup()
        sut.fetchNearStores()

        testObserver.assertValue(aNoStoresBusinessModel).assertValueCount(1).assertNoErrors()
        assertEquals(showViewPagerObserver.get(), false)
    }

    @Test
    fun testSaveLocation() {
        usecase.saveLocation(aLatLng)
    }

    @Test
    fun testResetStore() {
        val resetObserver = sut.reset.test()

        sut.resetStores()

        resetObserver.assertValue(Signal).assertNoErrors()
    }

    @Test
    fun testFetchFilterData() {
        given(usecase.getFilterDataStream()).willReturn(Observable.just(aFilterDataModel))
        val testObserver = sut.zoom.test()

        sut.fetchFilterData()

        testObserver.assertValue(16.0f).assertNoErrors()
    }
}
