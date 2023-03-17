package com.example.googlemapsapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.googlemapsapp.ui.theme.GoogleMapsAppTheme
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.libraries.places.api.Places


class MainActivity : ComponentActivity() {
    private var locationPermissionGranted =  mutableStateOf(false)
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1234
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Places.initialize(applicationContext, getString(R.string.MAPS_API_KEY))

        setContent {
            GoogleMapsAppTheme {
                if (locationPermissionGranted.value) {
                    HomeScreen()
                } else {
                    getLocationPermission()
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted.value = true
                }
            }
            else -> {
                locationPermissionGranted.value = false
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }


    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */

        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
        )
    }

}
