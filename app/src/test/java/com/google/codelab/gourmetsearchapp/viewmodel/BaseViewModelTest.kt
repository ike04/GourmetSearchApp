package com.google.codelab.gourmetsearchapp.viewmodel

import com.google.codelab.gourmetsearchapp.usecase.BaseUsecase
import com.google.codelab.gourmetsearchapp.usecase.Usecase
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class BaseViewModelTest {
    private class BaseViewModelImpl(private val usecase: Usecase) : BaseViewModel(
        usecase, Schedulers.trampoline(),
        Schedulers.trampoline()
    )

    @Mock
    private lateinit var sut: BaseViewModelImpl
    private lateinit var usecase: BaseUsecase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        sut = BaseViewModelImpl(usecase)
    }
}
