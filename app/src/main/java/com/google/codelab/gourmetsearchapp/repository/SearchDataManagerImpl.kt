package com.google.codelab.gourmetsearchapp.repository

import com.google.codelab.gourmetsearchapp.data.RemoteData
import javax.inject.Inject

class SearchDataManagerImpl @Inject constructor(
    private val remote: RemoteData
) : SearchDataManager {
}
