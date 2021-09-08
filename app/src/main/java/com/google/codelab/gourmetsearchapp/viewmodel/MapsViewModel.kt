package com.google.codelab.gourmetsearchapp.viewmodel

import com.google.codelab.gourmetsearchapp.usecase.MapsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(private val usecase: MapsUsecase) : BaseViewModel(usecase) {

}
