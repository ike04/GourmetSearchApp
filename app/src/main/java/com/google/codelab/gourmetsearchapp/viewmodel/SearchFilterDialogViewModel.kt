package com.google.codelab.gourmetsearchapp.viewmodel

import androidx.lifecycle.ViewModel
import com.google.codelab.gourmetsearchapp.Signal
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class SearchFilterDialogViewModel @Inject constructor() : ViewModel() {
    val onSearchClicked: PublishSubject<Signal> = PublishSubject.create()

    fun onSearchClick() {
        onSearchClicked.onNext(Signal)
    }
}
