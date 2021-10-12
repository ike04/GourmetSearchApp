package com.google.codelab.gourmetsearchapp.viewmodel

import com.google.codelab.gourmetsearchapp.Signal
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.MockitoAnnotations

class OnboardingViewModelTest {
    private lateinit var sut: OnboardingViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = OnboardingViewModel()
    }

    @Test
    fun testOnClick() {
        val testObserver = sut.onClick.test()

        sut.onClick()

        testObserver.assertValue(Signal).assertNoErrors()
    }
}
