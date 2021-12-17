package com.google.codelab.gourmetsearchapp.viewmodel

import com.google.codelab.gourmetsearchapp.Signal
import com.google.codelab.gourmetsearchapp.model.businessmodel.Store
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.usecase.MapsUsecase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

class MapsViewModelTest {
    private lateinit var sut: MapsViewModel

    @Mock
    private lateinit var usecase: MapsUsecase

    companion object {
        private val aStoresBusinessModel = StoresBusinessModel(totalPages = 1, getPages = 1, store = listOf(Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = "", isFavorite = false)))
        private val aNoStoresBusinessModel = StoresBusinessModel(totalPages = 0, getPages = 0, store = emptyList())
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        sut = MapsViewModel(usecase)
    }

    @Test
    fun testFetchNearStores() {
        given(usecase.getNearStores()).willReturn(Observable.just(aStoresBusinessModel))
        val testObserver = sut.storeList.test()
        val showViewPagerObserver = sut.showViewPager

        sut.fetchNearStores()

        testObserver.assertValue(aStoresBusinessModel).assertValueCount(1).assertNoErrors()
        assertTrue(showViewPagerObserver.get())
    }

    @Test
    fun testFetchNearStores_NoStores() {
        given(usecase.getNearStores()).willReturn(Observable.just(aNoStoresBusinessModel))
        val testObserver = sut.storeList.test()
        val showViewPagerObserver = sut.showViewPager

        sut.fetchNearStores()

        testObserver.assertValue(aNoStoresBusinessModel).assertValueCount(1).assertNoErrors()
        assertEquals(showViewPagerObserver.get(), false)
    }

    @Test
    fun testResetStore() {
        val resetObserver = sut.reset.test()

        sut.resetStores()

        resetObserver.assertValue(Signal).assertNoErrors()
    }
}
