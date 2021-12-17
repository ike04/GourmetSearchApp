package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.model.Failure
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

abstract class BaseUsecase constructor(
    @Named("subscribeOnScheduler") private val subscribeOnScheduler: Scheduler,
    @Named("observeOnScheduler") private val observeOnScheduler: Scheduler
): Usecase {
    val error: PublishSubject<Failure> = PublishSubject.create()
    protected val disposables = CompositeDisposable()

    protected fun <T : Any> Single<T>.execute(onSuccess: (T) -> Unit, retry: () -> Unit) {
        this
            .subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
            .subscribeBy(
                onSuccess = onSuccess,
                onError = {
                    error.onNext(Failure(it, it.toMessage(), retry))
                }
            ).addTo(disposables)
    }

    protected fun Throwable.toMessage(): Int {
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

    override fun dispose() {
        disposables.clear()
    }
}
