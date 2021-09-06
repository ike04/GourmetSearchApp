package com.google.codelab.gourmetsearchapp.viewmodel

import androidx.lifecycle.ViewModel
import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.model.Failure
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import retrofit2.HttpException

abstract class BaseViewModel: ViewModel() {
    protected val disposables = CompositeDisposable()
    val error: PublishSubject<Failure> = PublishSubject.create()

    protected fun <T: Any> Single<T>.execute(onSuccess: (T) -> Unit, retry: () -> Unit) {
        this
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = onSuccess,
                onError = {
                    error.onNext(Failure(it, it.toMessage(), retry))
                }
            ).addTo(disposables)
    }

    private fun Throwable.toMessage(): Int {
        return when (this) {
            is HttpException -> toMessage()
            else -> R.string.error_message_default
        }
    }

    private fun HttpException.toMessage(): Int {
        return when (code()) {
            404 -> R.string.error_message_404
            500 -> R.string.error_message_500
            else -> R.string.error_message_default
        }
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}
