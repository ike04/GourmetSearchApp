package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.model.Failure
import io.reactivex.rxjava3.core.Observable

interface Usecase {
    fun dispose()
    fun errorSignal(): Observable<Failure>
}
