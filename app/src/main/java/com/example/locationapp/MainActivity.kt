package com.example.locationapp

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.locationapp.ui.theme.LocationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: LocationViewModel = viewModel()

            // Sometimes you need to add dependency to able to add import

            LocationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(viewModel)
                }
            }
        }
    }
}

@Composable
fun MyApp(viewModel: LocationViewModel) {

    // LocalContext, which provides access to the current Android Context within
    // the Compose hierarchy. This is typically accessed using LocalContext.current.

    // For example, you might use LocalContext.current to get access to resources
    // like strings, colors, or themes, or to access Android system services like
    // the SharedPreferences or AssetManager.

    val context = LocalContext.current              // new   --- have to continue from here
    val locationUtils = LocationUtils(context)
    LocationDisplay(locationUtils = locationUtils, viewModel = viewModel, context = context)
}

@Composable
fun LocationDisplay(
    locationUtils: LocationUtils,
    viewModel: LocationViewModel,
    context: Context
) {

    val location = viewModel.location.value

    val address = location?.let {
        locationUtils.reverseGeocodeLocation(location)
    }

    // rememberLauncherForActivityResult():- It's typically used to manage the
    // lifecycle of an Activity result callback.

    // Show the pop-up and this will be the activity that we want to launch and
    // when something from this pop-up is returned give me that result

    // It registers a request to start a activity for result
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
                locationUtils.requestLocationUpdates(viewModel)
            }else {

                // Some common tasks that developers might use ActivityCompat for include
                // requesting permissions, handling permission results, starting activities
                // with transitions, and checking whether certain features are supported on the device.

                // ActivityCompat.shouldShowRequestPermissionRationale: -helps developers determine
                // whether they should show a rationale for requesting a particular permission.
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )

//                if (rationaleRequired) {
//                    Toast.makeText(context,
//                        "Location Permission is required for this feature to work", Toast.LENGTH_LONG)
//                        .show()
//                }else {
//                    Toast.makeText(context,
//                        "Location Permission is required Please enable it in the Android Settings",
//                        Toast.LENGTH_LONG).show()
//                }

                // This method returns true if the app has requested this permission previously
                // and the user denied the request. It returns false if the user's response was
                // "Never ask again," or if the permission has not been requested before.
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
        if (location != null) {
            Text(text = "Address: ${location.latitude} ${location.longitude} \n $address")
        }else {
            Text(text = "Location not available")
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = {
            if (locationUtils.hasLocationPermission(context)) {
                // Permission already granted, update the location

                locationUtils.requestLocationUpdates(viewModel)
            }else {
                // Request location permission
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            }
        }) {
            Text(text = "Get Location")
        }
    }
}







