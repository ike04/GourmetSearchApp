package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.Signal
import com.google.codelab.gourmetsearchapp.repository.FavoriteDataManager
import com.google.codelab.gourmetsearchapp.repository.SearchDataManager
import io.reactivex.rxjava3.core.Observable
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

class StoreWebViewUsecaseImplTest {
    private lateinit var sut: StoreWebViewUsecase

    @Mock
    private lateinit var repository: FavoriteDataManager

    companion object {
        private const val aStoreId = "J999999999"
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = StoreWebViewUsecaseImpl(repository)
    }

    @Test
    fun testAddFavoriteStore() {
        sut.addFavoriteStore(aStoreId)
    }

    @Test
    fun testDeleteFavoriteStore() {
        sut.deleteFavoriteStore(aStoreId)
    }

    @Test
    fun testHasFavoriteStore() {
        sut.hasFavoriteStore(aStoreId)
    }

    @Test
    fun testGetHasStoreIdStream() {
        given(repository.getHasStoreIdStream()).willReturn(Observable.just(true))

        val testObserver = sut.getHasStoreIdStream().test()

        testObserver.assertValue(true).assertNoErrors()
    }

    @Test
    fun testGetAddIdStream() {
        given(repository.getAddIdStream()).willReturn(Observable.just(Signal))

        val testObserver = sut.getAddIdStream().test()

        testObserver.assertValue(Signal).assertNoErrors()
    }

    @Test
    fun testGetDeleteIdStream() {
        given(repository.getDeleteIdStream()).willReturn(Observable.just(Signal))

        val testObserver = sut.getDeleteIdStream().test()

        testObserver.assertValue(Signal).assertNoErrors()
    }
}
