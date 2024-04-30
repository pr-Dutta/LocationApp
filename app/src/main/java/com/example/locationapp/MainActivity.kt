package com.example.locationapp

import android.content.Context
import android.os.Bundle
import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.locationapp.ui.theme.LocationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}

@Composable
fun LocationDisplay(
    locationUtils: LocationUtils,
    context: Context
) {

    // rememberLauncherForActivityResult():- It's typically used to manage the
    // lifecycle of an Activity result callback.

    // Show the pop-up and this will be the activity that we want to launch and
    // when something from this pop-up is returned give me that result
    val requestPermissionLauncher = rememberLauncherForActivityResult(

        // ActivityResultContracts:- It specifies the type of result you're expecting,
        contract = ActivityResultContracts.RequestMultiplePermissions(),

        //  A lambda that will be invoked with the result when it's available.
        // Inside the lambda passed to rememberLauncherForActivityResult(),
        // you handle the result obtained from the launched activity.
        onResult = {
            if (it[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                && it[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                // I have access to location
            }else {
                // 
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Location not available")
        Button(onClick = {
            if (locationUtils.hasLocationPermission(context)) {
                // Permission already granted, update the location
            }else {
                // Request location permission
            }
        }) {
            Text(text = "Get Location")
        }
    }
}






