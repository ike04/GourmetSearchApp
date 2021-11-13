package com.google.codelab.gourmetsearchapp.viewmodel

import com.google.codelab.gourmetsearchapp.Signal
import com.google.codelab.gourmetsearchapp.model.businessmodel.Store
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.usecase.HomeUsecase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertFalse
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
        private val aStoresBusinessModel = StoresBusinessModel(totalPages = 1, getPages = 1, store = listOf(Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = "")))
        private val aStore20 = (listOf(
            Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = ""),
            Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = ""),
            Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = ""),
            Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = ""),
            Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = ""),
            Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = ""),
            Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = ""),
            Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = ""),
            Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = ""),
            Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = ""),
            Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = ""),
            Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = ""),
            Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = ""),
            Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = ""),
            Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = ""),
            Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = ""),
            Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = ""),
            Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = ""),
            Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = ""),
            Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = "")))

        private val aStoresBusinessModel20 = StoresBusinessModel(totalPages = 20, getPages = 20, store = aStore20)
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        sut = HomeViewModel(usecase)
    }

    @Test
    fun testFetchStores_count_1() {
        given(usecase.fetchNearStores(1)).willReturn(Single.just(aStoresBusinessModel))
        val testSubscriber = sut.storeList.test()
        val emptyTestObserver = sut.isEmptyStores.test()

        sut.fetchStores(true)

        testSubscriber.assertValue(aStoresBusinessModel).assertValueCount(1).assertNoErrors()
        emptyTestObserver.assertValue(false)
        assertFalse(sut.moreLoad.get())
    }

    @Test
    fun testFetchStores_count_20() {
        given(usecase.fetchNearStores(1)).willReturn(Single.just(aStoresBusinessModel20))
        val testSubscriber = sut.storeList.test()

        sut.fetchStores()

        testSubscriber.assertValue(aStoresBusinessModel20).assertValueCount(1).assertNoErrors()
        assertTrue(sut.moreLoad.get())
    }

    @Test
    fun testFetchFavoriteStores_count_1() {
        given(usecase.getFavoriteStoreStream()).willReturn(Observable.just(aStoresBusinessModel))
        val testSubscriber = sut.storeList.test()

        sut.fetchFavoriteStores(true)

        testSubscriber.assertValue(aStoresBusinessModel).assertValueCount(1).assertNoErrors()
        assertFalse(sut.moreLoad.get())
    }

    @Test
    fun testFetchFavoriteStores_count_20() {
        given(usecase.getFavoriteStoreStream()).willReturn(Observable.just(aStoresBusinessModel20))
        val testSubscriber = sut.storeList.test()

        sut.fetchFavoriteStores()

        testSubscriber.assertValue(aStoresBusinessModel20).assertValueCount(1).assertNoErrors()
        assertTrue(sut.moreLoad.get())
    }

    @Test
    fun testResetPages() {
        sut.resetPages()

        assertTrue(sut.moreLoad.get())
    }

    @Test
    fun testResetStoreList() {
        val testObserver = sut.reset.test()

        sut.resetStoreList()

        testObserver.assertValue(Signal).assertNoErrors()
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
