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
                it.map { store ->
                    Store(
                        id = store.id,
                        name = store.name,
                        lat = store.lat,
                        lng = store.lng,
                        genre = store.genre.name,
                        budget = store.budget.average,
                        urls = store.urls.url,
                        photo = store.photo.photo.logo
                    )
                }
            } ?: emptyList()
        }
    }
}
