package com.google.codelab.gourmetsearchapp.usecase

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Named

abstract class BaseUsecase constructor(
    @Named("subscribeOnScheduler") private val subscribeOnScheduler: Scheduler,
    @Named("observeOnScheduler") private val observeOnScheduler: Scheduler
): Usecase {
    val error: PublishSubject<Pair<Throwable, () -> Unit>> = PublishSubject.create()
    val loadingTrigger: PublishSubject<Boolean> = PublishSubject.create()
    protected val disposables = CompositeDisposable()

    protected fun <T : Any> Single<T>.execute(onSuccess: (T) -> Unit, retry: () -> Unit) {
        this
            .subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
            .doOnSubscribe { loadingTrigger.onNext(true) }
            .doFinally { loadingTrigger.onNext(false) }
            .subscribeBy(
                onSuccess = onSuccess,
                onError = {
                    error.onNext(Pair(it, retry))
                }
            ).addTo(disposables)
    }

    override fun loadingSignal(): Observable<Boolean> = loadingTrigger.hide()

    override fun errorSignal(): Observable<Pair<Throwable, () -> Unit>> = error.hide()

    override fun dispose() {
        disposables.clear()
    }
}
