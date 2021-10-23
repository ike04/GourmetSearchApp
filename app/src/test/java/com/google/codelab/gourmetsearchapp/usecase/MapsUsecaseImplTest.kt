package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.model.businessmodel.Store
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.repository.SearchDataManager
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
        private val aStoresBusinessModel = StoresBusinessModel(getPages = 1, store = listOf(Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = "")))
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = MapsUsecaseImpl(repository)
    }

    @Test
    fun testFetchNearStores() {
        given(repository.fetchNearStores(1)).willReturn(Single.just(aStoresBusinessModel))

        val test = sut.fetchNearStores(1)

        test.test().assertValue(aStoresBusinessModel).assertNoErrors()
    }
}
