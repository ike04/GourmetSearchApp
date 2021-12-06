package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.model.FilterDataModel
import com.google.codelab.gourmetsearchapp.model.businessmodel.Store
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.repository.SearchDataManager
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

class MapsUsecaseImplTest {
    private lateinit var sut: MapsUsecase

    @Mock
    private lateinit var repository: SearchDataManager

    companion object {
        private val aStoresBusinessModel = StoresBusinessModel(totalPages = 1, getPages = 1, store = listOf(Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = "")))
        private val aFilterDataModel = FilterDataModel(searchRange = 1, genre = "イタリアン", coupon = 0, drink = 0, privateRoom = 0, wifi = 0, lunch = 0, keyword = "")
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = MapsUsecaseImpl(repository)
    }

    @Test
    fun testFetchNearStores() {
        given(repository.fetchNearStores(1)).willReturn(Single.just(aStoresBusinessModel))

        val testObserver = sut.fetchNearStores(1).test()

        testObserver.assertValue(aStoresBusinessModel).assertNoErrors()
    }

    @Test
    fun testFetchFilterData() {
        given(repository.getFilterDataStream()).willReturn(Observable.just(aFilterDataModel))
        val testObserver = sut.getFilterDataStream().test()

        sut.fetchFilterData()

        testObserver.assertValue(aFilterDataModel).assertNoErrors()
    }
}
