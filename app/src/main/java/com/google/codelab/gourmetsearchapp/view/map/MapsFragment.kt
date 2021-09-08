package com.google.codelab.gourmetsearchapp.view.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.databinding.FragmentMapsBinding
import com.google.codelab.gourmetsearchapp.util.MapUtils
import com.google.codelab.gourmetsearchapp.viewmodel.MapsViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class MapsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapsBinding
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    private val viewModel: MapsViewModel by viewModels()
    private val MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 1
    private var locationCallback: LocationCallback? = null
    private val disposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment?

        mapFragment?.getMapAsync(this)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        viewModel.storeList
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                it.store.map { store ->
                    MapUtils.addMarker(map, store, 0)
                }
            }.addTo(disposable)

        viewModel.error
            .subscribeBy { failure ->
                Snackbar.make(view, failure.message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry) { failure.retry }
                    .show()
            }.addTo(disposable)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        if (MapUtils.hasLocationPermission(requireContext())) {
            enableLocation()
        } else {
            MapUtils.requestLocationPermission(requireContext(), requireActivity())
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION -> {
                if (permissions.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 許可された
                    enableLocation()
                } else {
                    Toast.makeText(
                        requireContext(),
                        R.string.no_location_authorization,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun enableLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
            val locationRequest = LocationRequest().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    locationResult?.lastLocation?.let {
                        lastLocation = locationResult.lastLocation

                        val currentLatLng = LatLng(lastLocation.latitude, lastLocation.longitude)
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 14.0f))

                        viewModel.fetchNearStores(lastLocation.latitude, lastLocation.longitude)
                    }
                }
            }
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
