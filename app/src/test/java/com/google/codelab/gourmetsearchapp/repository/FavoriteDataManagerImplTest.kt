package com.google.codelab.gourmetsearchapp.repository

import com.google.codelab.gourmetsearchapp.Signal
import com.google.codelab.gourmetsearchapp.data.LocalStoreIdData
import com.google.codelab.gourmetsearchapp.data.RemoteData
import com.google.codelab.gourmetsearchapp.model.businessmodel.Store
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

class FavoriteDataManagerImplTest {
    private lateinit var sut: FavoriteDataManager

    @Mock
    private lateinit var remote: RemoteData

    @Mock
    private lateinit var local: LocalStoreIdData

    companion object {
        private val aStoreId = "J999999999"
        private val aStoreIdList = listOf("J999999999")
        private val aStoreList = Store(id = "J999999999", name = "レストラン", logo= "", lat = 10.0, lng =  10.0, genre = Genre("イタリアン"), budget = Budget("2000円", "2000円"),urls = Urls(""),photo = Photos(photo = Photo("")))
        private val aStoresResponse = StoresResponse(results = Results(apiVersion = "1.0.0", totalPages = 1, getPages = 1, store = listOf(aStoreList)))
        private val aStoresBusinessModel = StoresBusinessModel(totalPages = 1, getPages = 1, store = listOf(Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = "")))
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = FavoriteDataManagerImpl(remote, local)
    }

    @Test
    fun testFetchStoreIds() {
        given(sut.fetchStoreIds()).willReturn(Single.just(aStoreIdList))

        val testObserver = sut.fetchStoreIds().test()

        testObserver.assertValue(aStoreIdList).assertNoErrors()
    }

    @Test
    fun testFetchFavoriteStores() {
        given(remote.fetchFavoriteStores(aStoreId)).willReturn(Single.just(Response.success(aStoresResponse)))

        val testObserver = sut.fetchFavoriteStores(aStoreId).test()

        testObserver.assertValue(aStoresBusinessModel).assertNoErrors()
    }

    @Test
    fun testAddFavoriteStoreId() {
        sut.addFavoriteStoreId(aStoreId)
    }

    @Test
    fun testDeleteFavoriteStoreId() {
        sut.deleteFavoriteStoreId(aStoreId)
    }

    @Test
    fun testHasFavoriteStoreId() {
        sut.hasFavoriteStoreId(aStoreId)
    }

    @Test
    fun testGetStoreIdsStream() {
        given(local.getStoreIdsStream()).willReturn(Observable.just(aStoreIdList))

        val testObserver = sut.getStoreIdsStream().test()

        testObserver.assertValue(aStoreIdList).assertNoErrors()
    }

    @Test
    fun testGetHasStoreIdStream() {
        given(local.getHasStoreIdStream()).willReturn(Observable.just(true))

        val testObserver = sut.getHasStoreIdStream().test()

        testObserver.assertValue(true).assertNoErrors()
    }

    @Test
    fun testGetAddIdStream() {
        given(local.getAddIdStream()).willReturn(Observable.just(Signal))

        val testObserver = sut.getAddIdStream().test()

        testObserver.assertValue(Signal).assertNoErrors()
    }

    @Test
    fun testGetDeleteIdStream() {
        given(local.getDeleteIdStream()).willReturn(Observable.just(Signal))

        val testObserver = sut.getDeleteIdStream().test()

        testObserver.assertValue(Signal).assertNoErrors()
    }
}
