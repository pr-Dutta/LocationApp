package com.example.locationapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class LocationUtils(val context: Context) {

    fun hasLocationPermission(context: Context) : Boolean {

        // ContextCompat is a utility class in the Android Support Library used
        // to access resources and information related to the application's context,

        // checkSelfPermission: Determine whether you have granted a particular permission.
        return ContextCompat.checkSelfPermission(context,

            
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}