package com.google.codelab.gourmetsearchapp.usecase

import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseUsecase: Usecase {
    protected val disposables = CompositeDisposable()

    override fun dispose() {
        disposables.clear()
    }
}
