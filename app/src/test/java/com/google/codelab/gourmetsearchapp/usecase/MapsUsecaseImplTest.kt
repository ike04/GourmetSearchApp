package com.google.codelab.gourmetsearchapp.usecase

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.codelab.gourmetsearchapp.model.FilterDataModel
import com.google.codelab.gourmetsearchapp.model.NoLocationPermissionException
import com.google.codelab.gourmetsearchapp.model.businessmodel.Store
import com.google.codelab.gourmetsearchapp.model.businessmodel.StoresBusinessModel
import com.google.codelab.gourmetsearchapp.repository.FavoriteDataManager
import com.google.codelab.gourmetsearchapp.repository.LocationDataManager
import com.google.codelab.gourmetsearchapp.repository.SearchDataManager
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

class MapsUsecaseImplTest {
    private lateinit var sut: MapsUsecase

    @Mock
    private lateinit var repository: SearchDataManager

    @Mock
    private lateinit var favoriteRepository: FavoriteDataManager

    @Mock
    private lateinit var locationDataManager: LocationDataManager

    @Mock
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    companion object {
        private val aStoresBusinessModel = StoresBusinessModel(totalPages = 1, getPages = 1, store = listOf(Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = "", isFavorite = false)))
        private val aStoresBusinessModelWithFavorite = StoresBusinessModel(totalPages = 1, getPages = 1, store = listOf(Store(id = "J999999999", name = "レストラン", lat = 10.0, lng = 10.0, budget = "2000円", genre = "イタリアン", photo = "", urls = "", isFavorite = true)))
        private val aFilterDataModel = FilterDataModel(searchRange = 1, genre = "イタリアン", coupon = 0, drink = 0, privateRoom = 0, wifi = 0, lunch = 0, keyword = "")
        private val aStoreIds = listOf("J999999998")
        private val aStoreIdsWithinFavorite = listOf("J999999999")
        private val aLatLng = LatLng(10.0, 20.0)
        private val aThrowable = NoLocationPermissionException()
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = MapsUsecaseImpl(repository, favoriteRepository, locationDataManager)
    }

    @Test
    fun testFetchNearStores_without_favorite() {
        given(repository.fetchNearStores(1)).willReturn(Single.just(aStoresBusinessModel))
        given(favoriteRepository.fetchStoreIds()).willReturn(Single.just(aStoreIds))
        val testObserver = sut.getNearStores().test()

        sut.fetchNearStores()

        testObserver.assertValue(aStoresBusinessModel).assertNoErrors()
    }

    @Test
    fun testFetchNearStores_with_favorite() {
        given(repository.fetchNearStores(1)).willReturn(Single.just(aStoresBusinessModel))
        given(favoriteRepository.fetchStoreIds()).willReturn(Single.just(aStoreIdsWithinFavorite))
        val testObserver = sut.getNearStores().test()

        sut.fetchNearStores()

        testObserver.assertValue(aStoresBusinessModelWithFavorite).assertNoErrors()
    }

    @Test
    fun testFetchFilterData() {
        given(repository.getFilterDataStream()).willReturn(Observable.just(aFilterDataModel))
        val testObserver = sut.getFilterDataStream().test()

        sut.fetchFilterData()

        testObserver.assertValue(aFilterDataModel).assertNoErrors()
    }

    @Test
    fun testGetLocation() {
        locationDataManager.fetchLocation(fusedLocationProviderClient)
    }

    @Test
    fun testGetLocationStream() {
        given(locationDataManager.getLocationStream()).willReturn(Observable.just(aLatLng))

        val testObserver = sut.getLocationStream().test()

        testObserver.assertValue(aLatLng).assertNoErrors()
    }
}
