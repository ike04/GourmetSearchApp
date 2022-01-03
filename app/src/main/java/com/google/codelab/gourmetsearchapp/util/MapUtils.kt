package com.google.codelab.gourmetsearchapp.util

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.codelab.gourmetsearchapp.model.businessmodel.Store

object MapUtils {
    fun addMarker(map: GoogleMap, store: Store, position: Int): Int {
        val color = when (store.isFavorite) {
            true -> BitmapDescriptorFactory.HUE_ORANGE
            false -> BitmapDescriptorFactory.HUE_GREEN
        }
        val pin = map.addMarker(
            MarkerOptions()
                .position(LatLng(store.lat, store.lng))
                .icon(BitmapDescriptorFactory.defaultMarker(color))
        )
        pin?.tag = position
        return position + 1
    }
}
