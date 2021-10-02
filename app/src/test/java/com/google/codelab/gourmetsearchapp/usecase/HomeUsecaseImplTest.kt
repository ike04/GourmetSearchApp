package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.model.businessmodel.Store
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.repository.FavoriteDataManager
import com.google.codelab.gourmetsearchapp.repository.SearchDataManager
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
        private val aStoresBusinessModel = StoresBusinessModel(
            totalPages = 1,
            store = listOf(
                Store(
                    id = "J999999999",
                    name = "レストラン",
                    lat = 10.0,
                    lng = 10.0,
                    budget = "2000円",
                    genre = "イタリアン",
                    photo = "",
                    urls = ""
                )
            )
        )
        private val aNoStoresBusinessModel = StoresBusinessModel(totalPages = 1, store = emptyList())
        private val aStoreIdList = listOf("J999999999")
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        sut = HomeUsecaseImpl(repository, favoriteRepository)
    }

    @Test
    fun testFetchNearStores() {
        given(repository.fetchNearStores(1)).willReturn(Single.just(aStoresBusinessModel))

        val test  = sut.fetchNearStores(1).test()

        test.assertValue(aStoresBusinessModel).assertNoErrors()
    }

    @Test
    fun testFetchFavoriteStores() {
        given(favoriteRepository.fetchStoreIds()).willReturn(Single.just(aStoreIdList))
        given(favoriteRepository.fetchFavoriteStores(storeId)).willReturn(Single.just(aStoresBusinessModel))
        val testObserver = sut.getFavoriteStoreStream().test()

        sut.fetchFavoriteStores(false)

        testObserver.assertValue(aStoresBusinessModel).assertNoErrors()
    }
}
