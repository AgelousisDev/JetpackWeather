package com.agelousis.jetpackweather.utils.helpers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.agelousis.jetpackweather.utils.extensions.arePermissionsGranted
import com.google.android.gms.location.*

typealias LocationSuccessBlock = (Location) -> Unit

class LocationHelper(
        private val context: Context,
        private val removeLocationUpdates: Boolean = true,
        private val fastestInterval: Long? = null,
        private val interval: Long? = null,
        private val priority: Int? = null,
        private val smallestDisplacement: Float? = null,
        private val locationSuccessBlock: LocationSuccessBlock
): LocationCallback() {

    override fun onLocationResult(p0: LocationResult) {
        super.onLocationResult(p0)
        locationSuccessBlock(p0.lastLocation)
        if (removeLocationUpdates)
            LocationServices.getFusedLocationProviderClient(context).removeLocationUpdates(this)
    }

    private val locationRequest by lazy {
        val locationRequest = LocationRequest.create()
                .setPriority(priority ?: LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(interval ?: (10L * 1000))
                .setFastestInterval(fastestInterval ?: 10000)
        smallestDisplacement?.let {
            locationRequest.smallestDisplacement = it
        }
        locationRequest
    }

    init {
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)
        val locationSettingsRequest = builder.build()
        val settingsClient = LocationServices.getSettingsClient(context)
        settingsClient.checkLocationSettings(locationSettingsRequest)
        if (context.arePermissionsGranted(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
            LocationServices.getFusedLocationProviderClient(context).requestLocationUpdates(
                locationRequest,
                this,
                Looper.getMainLooper()
            )
    }

}