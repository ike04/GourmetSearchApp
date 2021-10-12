package com.google.codelab.gourmetsearchapp.viewmodel

import com.google.codelab.gourmetsearchapp.model.businessmodel.Store
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.usecase.HomeUsecase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

class HomeViewModelTest {
    private lateinit var sut: HomeViewModel

    @Mock
    private lateinit var usecase: HomeUsecase

    companion object {
        private val aStoresBusinessModel = StoresBusinessModel(totalPages = 1, store = listOf(Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = "")))
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        sut = HomeViewModel(usecase)
    }

    @Test
    fun testFetchStores() {
        given(usecase.fetchNearStores(1)).willReturn(Single.just(aStoresBusinessModel))
        val testSubscriber = sut.storeList.test()

        sut.fetchStores()

        testSubscriber.assertValue(aStoresBusinessModel).assertValueCount(1).assertNoErrors()
    }

    @Test
    fun testFetchFavoriteStores() {
        given(usecase.getFavoriteStoreStream()).willReturn(Observable.just(aStoresBusinessModel))
        val testSubscriber = sut.storeList.test()

        sut.fetchFavoriteStores(true)

        testSubscriber.assertValue(aStoresBusinessModel).assertValueCount(1).assertNoErrors()
    }

    @Test
    fun testResetPages() {
        sut.resetPages()

        assertTrue(sut.moreLoad.get())
    }

    @Test
    fun testCheckLocationPermission() {
        given(usecase.hasLocationPermission()).willReturn(Single.just(true))
        val testSubscriber = sut.hasLocation.test()

        sut.checkLocationPermission()

        testSubscriber.assertValue(true).assertNoErrors()
    }

    @Test
    fun testCheckLocationPermission_NoPermission() {
        given(usecase.hasLocationPermission()).willReturn(Single.just(false))
        val testSubscriber = sut.hasLocation.test()

        sut.checkLocationPermission()

        testSubscriber.assertValue(false).assertNoErrors()
    }
}
