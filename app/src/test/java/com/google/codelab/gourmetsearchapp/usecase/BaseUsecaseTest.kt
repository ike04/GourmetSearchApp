package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.viewmodel.BaseViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class BaseUsecaseTest {
    private class BaseUsecaseImpl : BaseUsecase(
        Schedulers.trampoline(),
        Schedulers.trampoline()
    )

    @Mock
    private lateinit var sut: BaseUsecaseImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        sut = BaseUsecaseImpl()
    }
}
