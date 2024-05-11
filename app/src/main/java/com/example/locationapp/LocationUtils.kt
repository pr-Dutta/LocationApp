package com.example.locationapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class LocationUtils(val context: Context) {

    // FusedLocationProviderClient allow to get the latitude and longitude
    // It will fused the location together so that we can work with it

    // The FusedLocationProviderClient is like that friend. It combines various
    // data sources to provide the best location information in an efficient way.
    // It’s a part of Google Play services and provides a simple API for getting location information.

    //LocationServices class is part of the Google Play services library,
    // It is typically used to interact with the device's location functionality, such as
    // retrieving the device's last known location, receiving location updates, geofencing, and more.

    // This is like saying, “Hey, I need to use your location-finding skills.” By calling this
    // function and giving it the context, you create an instance of FusedLocationProviderClient
    // that you can use to start asking for location updates.
    private val _fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)


    @SuppressLint("MissingPermission")
    fun requestLocationUpdates(viewModel: LocationViewModel) {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                locationResult.lastLocation?.let {
                    val location = LocationData(latitude = it.latitude, longitude = it.longitude)
                    viewModel.updateLocation(location)
                }
            }
        }

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

        _fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
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