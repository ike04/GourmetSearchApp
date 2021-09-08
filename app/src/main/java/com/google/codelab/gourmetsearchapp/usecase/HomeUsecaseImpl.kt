package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.repository.SearchDataManager
import javax.inject.Inject

class HomeUsecaseImpl @Inject constructor(
    private val repository: SearchDataManager
) : BaseUsecase(), HomeUsecase {
}
