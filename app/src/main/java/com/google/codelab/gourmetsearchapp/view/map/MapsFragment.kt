package com.google.codelab.gourmetsearchapp.view.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import autoCleared
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.codelab.gourmetsearchapp.R
import com.google.codelab.gourmetsearchapp.databinding.FragmentMapsBinding
import com.google.codelab.gourmetsearchapp.ext.showSnackBarWithAction
import com.google.codelab.gourmetsearchapp.ext.showSnackBarWithActionInfinity
import com.google.codelab.gourmetsearchapp.model.NoLocationPermissionException
import com.google.codelab.gourmetsearchapp.model.businessmodel.Store
import com.google.codelab.gourmetsearchapp.util.MapUtils
import com.google.codelab.gourmetsearchapp.view.webview.WebViewActivity
import com.google.codelab.gourmetsearchapp.viewmodel.MainViewModel
import com.google.codelab.gourmetsearchapp.viewmodel.MapsViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class MapsFragment : Fragment(), OnMapReadyCallback {
    private var binding: FragmentMapsBinding by autoCleared()
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val viewModel: MapsViewModel by activityViewModels()
    private val parentViewModel: MainViewModel by activityViewModels()
    private var mapMarkerPosition = 0
    private val storeList: MutableList<Store> = ArrayList()
    private val disposable = CompositeDisposable()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            viewModel.getLocation(fusedLocationProviderClient)
        } else {
            showSnackBarWithActionInfinity(R.string.no_locations_message) {
                checkLocationPermission()
            }
        }
    }

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
        viewModel.setup()

        return binding.root
    }

    @SuppressLint("MissingPermission")
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

                val intent = Intent(requireContext(), WebViewActivity::class.java)
                    .putExtra(WebViewActivity.ID, storeList[position].id)
                    .putExtra(WebViewActivity.URL, storeList[position].urls)
                startActivity(intent)
            }

        viewModel.latLng
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                if (storeList.isEmpty()) {
                    viewModel.fetchFilterData()
                }
                map.moveCamera(CameraUpdateFactory.newLatLng(it))
                map.isMyLocationEnabled = true
            }.addTo(disposable)

        viewModel.storeList
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                it.store.map { store ->
                    mapMarkerPosition = MapUtils.addMarker(map, store, mapMarkerPosition)
                    storeList.add(store)
                }
                binding.storePager.adapter?.notifyDataSetChanged()
                Toast.makeText(
                    requireContext(),
                    getString(R.string.around_store_count, storeList.size),
                    Toast.LENGTH_LONG
                ).show()
            }.addTo(disposable)

        viewModel.zoom
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                map.moveCamera(CameraUpdateFactory.zoomTo(it))
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
                when (failure.error) {
                    is NoLocationPermissionException -> checkLocationPermission()
                    else -> showSnackBarWithAction(failure)
                }
            }.addTo(disposable)

        parentViewModel.reselectItem
            .filter { it.isMap() }
            .subscribeBy {
                if (storeList.isNotEmpty()) {
                    binding.storePager.setCurrentItem(0, true)
                    val selectedStoreLatLng =
                        LatLng(storeList[0].lat, storeList[0].lng)
                    map.moveCamera(CameraUpdateFactory.newLatLng(selectedStoreLatLng))
                }
            }.addTo(disposable)

        binding.storePager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        viewModel.getLocation(fusedLocationProviderClient)

        map.setOnMyLocationButtonClickListener {
            viewModel.resetStores()
            viewModel.fetchNearStores()
            true
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

    private fun checkLocationPermission() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
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

    override fun onPause() {
        super.onPause()
        viewModel.stopLocationUpdates(fusedLocationProviderClient)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
