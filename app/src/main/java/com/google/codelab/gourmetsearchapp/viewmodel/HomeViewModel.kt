package com.google.codelab.gourmetsearchapp.viewmodel

import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.usecase.HomeUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val usecase: HomeUsecase
) : BaseViewModel(usecase) {
    val storeList: PublishSubject<StoresBusinessModel> = PublishSubject.create()

    fun fetchStores(startPage: Int = 1) {
        usecase.fetchNearStores()
            .execute(
                onSuccess = { storeList.onNext(it) },
                retry = { fetchStores(startPage) }
            )
    }
}
