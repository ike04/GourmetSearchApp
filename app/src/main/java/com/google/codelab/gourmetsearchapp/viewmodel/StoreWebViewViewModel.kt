package com.google.codelab.gourmetsearchapp.viewmodel

import androidx.databinding.ObservableBoolean
import com.google.codelab.gourmetsearchapp.Signal
import com.google.codelab.gourmetsearchapp.usecase.StoreWebViewUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class StoreWebViewViewModel @Inject constructor(
    private val usecase: StoreWebViewUsecase
) : BaseViewModel(usecase) {
    val addFavoriteStore: PublishSubject<Signal> = PublishSubject.create()
    val deleteFavoriteStore: PublishSubject<Signal> = PublishSubject.create()
    val errorStream: PublishSubject<Signal> = PublishSubject.create()
    val hasFavoriteStore = ObservableBoolean()

    // すでにお気に入りに登録済みかどうかをチェックする
    fun fetchFavoriteStore(storeId: String) {
        usecase.hasFavoriteStore(storeId)
            .subscribeBy(
                onSuccess = { isFavorite ->
                    hasFavoriteStore.set(isFavorite)
                },
                onError = {
                    errorStream.onNext(Signal)
                }
            ).addTo(disposables)
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
            .subscribeBy(
                onComplete = {
                    addFavoriteStore.onNext(Signal)
                    toggleFavorite(hasFavoriteStore.get())
                },
                onError = {
                    errorStream.onNext(Signal)
                }
            ).addTo(disposables)
    }

    private fun deleteFavoriteStore(storeId: String) {
        usecase.deleteFavoriteStore(storeId)
            .subscribeBy(
                onComplete = {
                    deleteFavoriteStore.onNext(Signal)
                    toggleFavorite(hasFavoriteStore.get())
                },
                onError = {
                    errorStream.onNext(Signal)
                }
            ).addTo(disposables)
    }

    private fun toggleFavorite(isFavorite: Boolean) {
        hasFavoriteStore.set(!isFavorite)
    }

}
