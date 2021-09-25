package com.google.codelab.gourmetsearchapp.viewmodel

import androidx.lifecycle.ViewModel
import com.google.codelab.gourmetsearchapp.Signal
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class SwitchHomeListViewModel @Inject constructor() : ViewModel() {
    val onClick: PublishSubject<Signal> = PublishSubject.create()

    fun onClicked() {
        onClick.onNext(Signal)
    }
}
