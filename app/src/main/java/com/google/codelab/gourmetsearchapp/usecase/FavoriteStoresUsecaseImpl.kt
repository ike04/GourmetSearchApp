package com.google.codelab.gourmetsearchapp.usecase

import com.google.codelab.gourmetsearchapp.repository.SearchDataManager
import javax.inject.Inject

class FavoriteStoresUsecaseImpl @Inject constructor(
    private val repository: SearchDataManager
) : BaseUsecase(), FavoriteStoresUsecase {
}
