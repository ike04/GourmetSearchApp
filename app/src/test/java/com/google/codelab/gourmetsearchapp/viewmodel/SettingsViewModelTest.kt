package com.google.codelab.gourmetsearchapp.viewmodel

import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class SettingsViewModelTest {
    private lateinit var sut: SettingsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        sut = SettingsViewModel()
    }

    @Test
    fun test() {
        // nothing
    }
}
