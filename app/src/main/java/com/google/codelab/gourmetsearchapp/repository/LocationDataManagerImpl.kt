package com.google.codelab.gourmetsearchapp.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.google.codelab.gourmetsearchapp.model.NoLocationPermissionException
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class LocationDataManagerImpl @Inject constructor(
    private val context: Context
) : LocationDataManager {
    private var locationCallback: LocationCallback? = null
    private val location: PublishSubject<LatLng> = PublishSubject.create()

    override fun fetchLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ): Completable {
        val permission = context.packageManager.checkPermission(
            Manifest.permission.ACCESS_FINE_LOCATION,
            context.packageName
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            return Completable.error(NoLocationPermissionException())
        }
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.let { super.onLocationResult(it) }
                locationResult?.lastLocation?.let { lastLocation ->
                    val currentLatLng = LatLng(lastLocation.latitude, lastLocation.longitude)
                    location.onNext(currentLatLng)
                }
            }
        }

        locationCallback?.let {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
        return Completable.complete()
    }

    override fun getLocationStream(): Observable<LatLng> = location.hide()
}
