package com.google.codelab.gourmetsearchapp.repository

import com.google.codelab.gourmetsearchapp.data.LocalData
import com.google.codelab.gourmetsearchapp.data.RemoteData
import javax.inject.Inject

class FavoriteDataManagerImpl @Inject constructor(
    private val remote: RemoteData,
    private val local: LocalData
) : FavoriteDataManager {
}
