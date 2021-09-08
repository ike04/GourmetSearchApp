package com.google.codelab.gourmetsearchapp.viewmodel

import com.google.codelab.gourmetsearchapp.usecase.HomeUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val usecase: HomeUsecase
) : BaseViewModel(usecase) {

    fun fetchStores(startPage: Int) {
        usecase.fetchNearStores()
            .execute(
                onSuccess = { },
                retry = { fetchStores(startPage) }
            )
    }
}
