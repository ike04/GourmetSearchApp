package com.google.codelab.gourmetsearchapp.viewmodel

import androidx.annotation.IdRes
import androidx.lifecycle.ViewModel
import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.view.MainBottomNavigationSelectedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val reselectItem: PublishSubject<MainBottomNavigationSelectedItem> = PublishSubject.create()

    fun reselectBottomNavigationItemOnRoot(@IdRes selectedMenuId: Int) {
        val reselected = when (selectedMenuId) {

            R.id.navigation_home -> MainBottomNavigationSelectedItem.HOME
            R.id.navigation_map -> MainBottomNavigationSelectedItem.MAP
            R.id.navigation_setting -> MainBottomNavigationSelectedItem.SETTINGS
            else -> return
        }

        reselectItem.onNext(reselected)
    }
}
