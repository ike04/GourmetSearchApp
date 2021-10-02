package com.google.codelab.gourmetsearchapp.viewmodel

import com.google.codelab.gourmetsearchapp.Signal
import com.google.codelab.gourmetsearchapp.model.FilterDataModel
import com.google.codelab.gourmetsearchapp.usecase.SearchFilterDialogUsecase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

class SearchFilterDialogViewModelTest {
    private lateinit var sut: SearchFilterDialogViewModel

    @Mock
    private lateinit var usecase: SearchFilterDialogUsecase

    companion object {
        private val aFilterDataModel = FilterDataModel(searchRange = 1,genre = "イタリアン",coupon = 0,drink = 0,privateRoom = 0,wifi = 0,lunch = 0,keyword = "")
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        sut = SearchFilterDialogViewModel(usecase)
    }

    @Test
    fun testFetchFilterData() {
        given(usecase.getFilterDataStream()).willReturn(Observable.just(aFilterDataModel))
        val testObserver = sut.filterData.test()

        sut.fetchFilterData()

        testObserver.assertValue(aFilterDataModel).assertValueCount(1).assertNoErrors()
    }

    @Test
    fun testOnSearchClick() {
        val testObserver = sut.onSearchClicked.test()

        sut.onSearchClick()

        testObserver.assertValue(Signal).assertNoErrors()
    }

    @Test
    fun testOnResetClick() {
        val testObserver = sut.onResetClicked.test()

        sut.onResetClick()

        testObserver.assertValue(Signal).assertNoErrors()
    }

    @Test
    fun testOnCancelClick() {
        val testObserver = sut.onCancelClicked.test()

        sut.onCancelClick()

        testObserver.assertValue(Signal).assertNoErrors()
    }
}
