package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.model.FilterDataModel
import com.google.codelab.gourmetsearchapp.repository.SearchDataManager
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

class SearchFilterDialogUsecaseImplTest {
    private lateinit var sut: SearchFilterDialogUsecase

    @Mock
    private lateinit var repository: SearchDataManager

    companion object {
        private val aFilterDataModel = FilterDataModel(searchRange = 1, genre = "イタリアン", coupon = 0, drink = 0, privateRoom = 0, wifi = 0, lunch = 0, keyword = "")
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        sut = SearchFilterDialogUsecaseImpl(repository)
    }

    @Test
    fun testSaveFilterData() {
        sut.saveFilterData(aFilterDataModel)
    }

    @Test
    fun testResetFilterData() {
        sut.resetFilterData()
    }

    @Test
    fun testFetchFilterData() {
        given(repository.getFilterDataStream()).willReturn(Observable.just(aFilterDataModel))
        val testObserver = sut.getFilterDataStream().test()

        sut.fetchFilterData()

        testObserver.assertValue(aFilterDataModel).assertNoErrors()
    }

    @Test
    fun testGetHasLocationPermissionStream() {
        given(repository.hasLocationPermission()).willReturn(Single.just(true))

        val testObserver = sut.getHasLocationPermissionStream().test()

        testObserver.assertValue(true).assertNoErrors()

    }
}
