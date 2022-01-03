package com.google.codelab.gourmetsearchapp.viewmodel

import com.google.codelab.gourmetsearchapp.Signal
import com.google.codelab.gourmetsearchapp.model.Failure
import com.google.codelab.gourmetsearchapp.usecase.StoreWebViewUsecase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

class StoreWebViewViewModelTest {
    private lateinit var sut: StoreWebViewViewModel

    private lateinit var anErrorSubject: PublishSubject<Pair<Throwable, () -> Unit>>

    @Mock
    private lateinit var usecase: StoreWebViewUsecase

    companion object {
        private const val storeId = "J999999999"
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        anErrorSubject = PublishSubject.create()
        given(usecase.errorSignal()).willReturn(anErrorSubject)
        given(usecase.loadingSignal()).willReturn(Observable.just(false))

        sut = StoreWebViewViewModel(usecase)
    }

    @Test
    fun testFetchFavoriteStore_true() {
        given(usecase.getHasStoreIdStream()).willReturn(Observable.just(true))

        sut.fetchFavoriteStore(storeId)

        assertTrue(sut.hasFavoriteStore.get())
    }

    @Test
    fun testFetchFavoriteStore_false() {
        given(usecase.getHasStoreIdStream()).willReturn(Observable.just(false))

        sut.fetchFavoriteStore(storeId)

        assertFalse(sut.hasFavoriteStore.get())
    }

    @Test
    fun testOnClickFab_add() {
        given(usecase.getAddIdStream()).willReturn(Observable.just(Signal))
        val testObserver = sut.addFavoriteStore.test()

        sut.hasFavoriteStore.set(false)
        sut.onClickFab(storeId)
        usecase.addFavoriteStore(storeId)

        testObserver.assertValue(Signal).assertNoErrors()
        assertTrue(sut.hasFavoriteStore.get())
    }

    @Test
    fun testOnClickFab_delete() {
        given(usecase.getDeleteIdStream()).willReturn(Observable.just(Signal))
        val testObserver = sut.deleteFavoriteStore.test()

        sut.hasFavoriteStore.set(true)
        sut.onClickFab(storeId)
        usecase.deleteFavoriteStore(storeId)

        testObserver.assertValue(Signal).assertNoErrors()
        assertFalse(sut.hasFavoriteStore.get())
    }
}
