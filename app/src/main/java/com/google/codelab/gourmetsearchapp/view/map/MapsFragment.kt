package com.google.codelab.gourmetsearchapp.view.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.databinding.FragmentMapsBinding
import com.google.codelab.gourmetsearchapp.ext.showFragment
import com.google.codelab.gourmetsearchapp.model.businessmodel.Store
import com.google.codelab.gourmetsearchapp.util.MapUtils
import com.google.codelab.gourmetsearchapp.view.webview.StoreWebViewFragment
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

    private val viewModel: MapsViewModel by activityViewModels()
    private val MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 1
    private var locationCallback: LocationCallback? = null
    private var mapMarkerPosition = 0
    private val storeList: MutableList<Store> = ArrayList()
    private val disposable = CompositeDisposable()

    companion object {
        fun newInstance(): MapsFragment {
            return MapsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment?

        mapFragment?.getMapAsync(this)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        binding.storePager.adapter =
            PagerStoreAdapter(storeList) {
                val position = binding.storePager.currentItem

                StoreWebViewFragment.newInstance(
                    storeList[position].id,
                    storeList[position].urls
                ).showFragment(parentFragmentManager)
            }

        viewModel.storeList
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                it.store.map { store ->
                    mapMarkerPosition = MapUtils.addMarker(map, store, mapMarkerPosition)
                    storeList.add(store)
                }
                binding.storePager.adapter?.notifyDataSetChanged()
                Toast.makeText(requireContext(),"周辺のレストランが${storeList.size}件見つかりました",Toast.LENGTH_LONG).show()
            }.addTo(disposable)

        viewModel.reset
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                storeList.clear()
                map.clear()
                mapMarkerPosition = 0
            }
            .addTo(disposable)

        viewModel.error
            .subscribeBy { failure ->
                Snackbar.make(view, failure.message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry) { failure.retry }
                    .show()
            }.addTo(disposable)

        binding.storePager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        if (MapUtils.hasLocationPermission(requireContext())) {
            enableLocation()
        } else {
            MapUtils.requestLocationPermission(requireContext(), requireActivity())
        }

        map.setOnMarkerClickListener {
            binding.storePager.setCurrentItem(it.tag as Int, true)
            true
        }

        binding.storePager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (storeList.isNotEmpty()) {
                    val selectedStoreLatLng =
                        LatLng(storeList[position].lat, storeList[position].lng)
                    map.moveCamera(CameraUpdateFactory.newLatLng(selectedStoreLatLng))
                }
            }
        })
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

                        viewModel.saveLocation(currentLatLng)
                        viewModel.fetchNearStores()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter -> {
                SearchFilterDialogFragment.newInstance().show(childFragmentManager, "")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
