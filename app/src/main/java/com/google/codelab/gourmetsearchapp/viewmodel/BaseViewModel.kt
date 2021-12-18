package com.google.codelab.gourmetsearchapp.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.model.Failure
import com.google.codelab.gourmetsearchapp.usecase.Usecase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Named

abstract class BaseViewModel constructor(
    private val usecase: Usecase,
    @Named("subscribeOnScheduler") private val subscribeOnScheduler: Scheduler,
    @Named("observeOnScheduler") private val observeOnScheduler: Scheduler
) : ViewModel() {
    protected val disposables = CompositeDisposable()
    val error: PublishSubject<Failure> = PublishSubject.create()
    val loadingSignal = ObservableBoolean(false)

    init {
        usecase.errorSignal()
            .subscribeBy { error.onNext(it) }
            .addTo(disposables)
    }

    protected fun <T : Any> Single<T>.execute(onSuccess: (T) -> Unit, retry: () -> Unit) {
        this
            .subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
            .doOnSubscribe { loadingSignal.set(true) }
            .doFinally { loadingSignal.set(false) }
            .subscribeBy(
                onSuccess = onSuccess,
                onError = {
                    error.onNext(Failure(it, it.toMessage(), retry))
                }
            ).addTo(disposables)
    }

    protected fun <T : Any> Observable<T>.execute(onSuccess: (T) -> Unit, retry: () -> Unit) {
        this
            .subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
            .doOnSubscribe { loadingSignal.set(true) }
            .doFinally { loadingSignal.set(false) }
            .subscribeBy(
                onNext = onSuccess,
                onError = {
                    error.onNext(Failure(it, it.toMessage(), retry))
                }
            ).addTo(disposables)
    }

    private fun Throwable.toMessage(): Int {
        return when (this) {
            is HttpException -> toMessage()
            is UnknownHostException -> R.string.error_offline
            else -> R.string.error_unexpected
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
        super.onCleared()
        disposables.clear()
        usecase.dispose()
    }
}
