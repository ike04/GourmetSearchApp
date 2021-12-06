package com.google.codelab.gourmetsearchapp.viewmodel

import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.view.MainBottomNavigationSelectedItem
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainViewModelTest {
    private lateinit var sut: MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        sut = MainViewModel()
    }

    @Test
    fun testReselectBottomNavigationItemOnRoot_home() {
        val testObserver= sut.reselectItem.test()

        sut.reselectBottomNavigationItemOnRoot(R.id.navigation_home)

        testObserver.assertValue(MainBottomNavigationSelectedItem.HOME)
    }

    @Test
    fun testReselectBottomNavigationItemOnRoot_map() {
        val testObserver= sut.reselectItem.test()

        sut.reselectBottomNavigationItemOnRoot(R.id.navigation_map)

        testObserver.assertValue(MainBottomNavigationSelectedItem.MAP)
    }

    @Test
    fun testReselectBottomNavigationItemOnRoot_setting() {
        val testObserver= sut.reselectItem.test()

        sut.reselectBottomNavigationItemOnRoot(R.id.navigation_setting)

        testObserver.assertValue(MainBottomNavigationSelectedItem.SETTINGS)
    }

    @Test
    fun testReselectBottomNavigationItemOnRoot_other() {
        val testObserver= sut.reselectItem.test()

        sut.reselectBottomNavigationItemOnRoot(0)

        testObserver.assertEmpty()
    }
}
