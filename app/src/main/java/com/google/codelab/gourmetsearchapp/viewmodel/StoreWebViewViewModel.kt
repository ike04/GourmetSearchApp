package com.google.codelab.gourmetsearchapp.viewmodel

import androidx.databinding.ObservableBoolean
import com.google.codelab.gourmetsearchapp.Signal
import com.google.codelab.gourmetsearchapp.usecase.StoreWebViewUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class StoreWebViewViewModel @Inject constructor(
    private val usecase: StoreWebViewUsecase
) : BaseViewModel(usecase) {
    val addFavoriteStore: PublishSubject<Signal> = PublishSubject.create()
    val deleteFavoriteStore: PublishSubject<Signal> = PublishSubject.create()
    val hasFavoriteStore = ObservableBoolean()

    fun fetchFavoriteStore(storeId: String) {
        usecase.hasFavoriteStore(storeId)

        usecase.getHasStoreIdStream()
            .execute(
                onSuccess = { hasFavoriteStore.set(it) },
                retry = { usecase.hasFavoriteStore(storeId) }
            )
    }

    fun onClickFab(storeId: String) {
        if (hasFavoriteStore.get()) {
            deleteFavoriteStore(storeId)
        } else {
            addFavoriteStore(storeId)
        }
    }

    private fun addFavoriteStore(storeId: String) {
        usecase.addFavoriteStore(storeId)

        usecase.getAddIdStream()
            .execute(
                onSuccess = {
                    addFavoriteStore.onNext(Signal)
                    toggleFavorite(hasFavoriteStore.get())
                },
                retry = { usecase.addFavoriteStore(storeId) }
            )
    }

    private fun deleteFavoriteStore(storeId: String) {
        usecase.deleteFavoriteStore(storeId)

        usecase.getDeleteIdStream()
            .execute(
                onSuccess = {
                    deleteFavoriteStore.onNext(Signal)
                    toggleFavorite(hasFavoriteStore.get())
                },
                retry = { usecase.deleteFavoriteStore(storeId) }
            )
    }

    private fun toggleFavorite(isFavorite: Boolean) {
        hasFavoriteStore.set(!isFavorite)
    }

}
