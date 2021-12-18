package com.google.codelab.gourmetsearchapp.viewmodel

import com.google.codelab.gourmetsearchapp.model.Failure
import com.google.codelab.gourmetsearchapp.usecase.BaseUsecase
import com.google.codelab.gourmetsearchapp.usecase.Usecase
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

class BaseViewModelTest {
    private class BaseViewModelImpl(private val usecase: Usecase) : BaseViewModel(
        usecase, Schedulers.trampoline(),
        Schedulers.trampoline()
    )

    @Mock
    private lateinit var sut: BaseViewModelImpl
    private lateinit var usecase: BaseUsecase

    private lateinit var anErrorSubject: PublishSubject<Failure>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        anErrorSubject = PublishSubject.create()
        given(usecase.errorSignal()).willReturn(anErrorSubject)
        sut = BaseViewModelImpl(usecase)
    }
}
