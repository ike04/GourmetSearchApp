package com.google.codelab.gourmetsearchapp.model.businessmodel

import com.google.codelab.gourmetsearchapp.model.response.StoresResponse
import retrofit2.Response

class StoresMapper {
    companion object {
        fun transform(response: Response<StoresResponse>): StoresBusinessModel {
            return StoresBusinessModel(
                totalPages = response.body()?.results?.totalPages ?: 0,
                getPages = response.body()?.results?.getPages ?: 0,
                store = transformStore(response.body())
            )
        }

        private fun transformStore(response: StoresResponse?): List<Store> {
            return response?.results?.store?.let {
                it.map {
                    Store(
                        id = it.id,
                        name = it.name,
                        lat = it.lat,
                        lng = it.lng,
                        genre = it.genre.name,
                        budget = it.budget.average,
                        urls = it.urls.url,
                        photo = it.photo.photo.logo
                    )
                }
            } ?: emptyList()
        }
    }
}
