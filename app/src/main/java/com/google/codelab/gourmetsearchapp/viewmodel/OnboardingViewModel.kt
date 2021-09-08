package com.google.codelab.gourmetsearchapp.viewmodel

import com.google.codelab.gourmetsearchapp.Signal
import com.google.codelab.gourmetsearchapp.usecase.OnboardingUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val usecase: OnboardingUsecase) : BaseViewModel(usecase) {
    val onClick: PublishSubject<Signal> = PublishSubject.create()

    fun onClick(){
        onClick.onNext(Signal)
    }
}
