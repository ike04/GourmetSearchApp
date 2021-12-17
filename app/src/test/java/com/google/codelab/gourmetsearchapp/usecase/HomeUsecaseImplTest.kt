package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.model.businessmodel.Store
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.repository.FavoriteDataManager
import com.google.codelab.gourmetsearchapp.repository.SearchDataManager
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

class HomeUsecaseImplTest {
    private lateinit var sut: HomeUsecase

    @Mock
    private lateinit var repository: SearchDataManager
    @Mock
    private lateinit var favoriteRepository: FavoriteDataManager

    companion object {
        private const val storeId = "J999999999"
        private val aStoresBusinessModel = StoresBusinessModel(totalPages = 1, getPages = 1, store = listOf(Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = "", isFavorite = false)))
        private val aEmptyStoresBusinessModel = StoresBusinessModel(totalPages = 0, getPages = 0, store = emptyList())
        private val aStoreIdList = listOf("J999999999")
        private val aEmptyStoreIdList = emptyList<String>()
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        sut = HomeUsecaseImpl(repository, favoriteRepository)
    }

    @Test
    fun testFetchNearStores() {
        given(repository.fetchNearStores(1)).willReturn(Single.just(aStoresBusinessModel))

        val testObserver  = sut.fetchNearStores(1).test()

        testObserver.assertValue(aStoresBusinessModel).assertNoErrors()
    }

    @Test
    fun testFetchFavoriteStores_Update() {
        given(favoriteRepository.fetchStoreIds()).willReturn(Single.just(aStoreIdList))
        given(favoriteRepository.fetchFavoriteStores(storeId)).willReturn(Single.just(aStoresBusinessModel))
        val testObserver = sut.getFavoriteStoreStream().test()

        sut.fetchFavoriteStores(true)

        testObserver.assertValue(aStoresBusinessModel).assertNoErrors()
    }

    @Test
    fun testFetchFavoriteStores_notForceUpdate() {
        given(favoriteRepository.fetchStoreIds()).willReturn(Single.just(aStoreIdList))
        given(favoriteRepository.fetchFavoriteStores(storeId)).willReturn(Single.just(aStoresBusinessModel))
        val testObserver = sut.getFavoriteStoreStream().test()

        sut.fetchFavoriteStores(false)

        testObserver.assertValue(aStoresBusinessModel).assertNoErrors()
    }

    @Test
    fun testFetchFavoriteStores_withNoFavoriteIds() {
        given(favoriteRepository.fetchStoreIds()).willReturn(Single.just(aEmptyStoreIdList))
        given(favoriteRepository.fetchFavoriteStores("")).willReturn(Single.just(aEmptyStoresBusinessModel))
        val testObserver = sut.getFavoriteStoreStream().test()

        sut.fetchFavoriteStores(true)

        testObserver.assertValue(aEmptyStoresBusinessModel).assertNoErrors()
    }

    @Test
    fun testHasLocationPermission() {
        given(repository.hasLocationPermission()).willReturn(Single.just(true))

        val testObserver = sut.hasLocationPermission().test()

        testObserver.assertValue(true)
    }

    @Test
    fun testGetStoreIdsStream() {
        given(favoriteRepository.getStoreIdsStream()).willReturn(Observable.just(aStoreIdList))

        val testObserver = sut.getStoreIdsStream().test()

        testObserver.assertValue(aStoreIdList)
    }
}
