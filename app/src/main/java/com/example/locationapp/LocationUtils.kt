package com.example.locationapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices

class LocationUtils(val context: Context) {

    private val _fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun requestLocationUpdates(viewModel: LocationViewModel) {
        val locationCallback = object : LocationCallback() {
            
        }
    }

    fun hasLocationPermission(context: Context) : Boolean {

        // ContextCompat is a utility class in the Android Support Library used
        // to access resources and information related to the application's context,

        // checkSelfPermission: Determine whether you have granted a particular permission.
        return ContextCompat.checkSelfPermission(context,

            // The PackageManager class in Android development is a key component for
            // querying and accessing information about installed applications
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}