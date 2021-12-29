package com.google.codelab.gourmetsearchapp.usecase

import io.reactivex.rxjava3.core.Observable

interface Usecase {
    fun dispose()
    fun loadingSignal(): Observable<Boolean>
    fun errorSignal(): Observable<Pair<Throwable, () -> Unit>>
}
