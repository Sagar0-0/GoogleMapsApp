package com.example.googlemapsapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.maps.android.compose.*


private var locationPermissionGranted = mutableStateOf(false)
private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1234

@OptIn(MapsComposeExperimentalApi::class)
@SuppressLint("MissingPermission")
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val placesClient = Places.createClient(context)
    val fusedLocationProviderClient =
        remember { LocationServices.getFusedLocationProviderClient(context) }
    var lastKnownLocation: Location? = null
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 14f)
    }
    val deviceLocation = remember {
        mutableStateOf(LatLng(0.0, 0.0))
    }

    try {
        val locationResult = fusedLocationProviderClient.lastLocation
        locationResult.addOnCompleteListener(context as MainActivity) { task ->
            if (task.isSuccessful) {
                // Set the map's camera position to the current location of the device.
                lastKnownLocation = task.result
                if (lastKnownLocation != null) {
                    deviceLocation.value=LatLng(
                        lastKnownLocation!!.latitude,
                        lastKnownLocation!!.longitude
                    )
                }
            } else {
                Log.d(TAG, "Current location is null. Using defaults.")
                Log.e(TAG, "Exception: %s", task.exception)
            }
        }
    } catch (e: SecurityException) {
        Log.e("Exception: %s", e.message, e)
    }

    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(mapToolbarEnabled = false)
        )
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            uiSettings = mapUiSettings,
            cameraPositionState = cameraPositionState
        ) {
            MarkerInfoWindowContent(
                state = MarkerState(
                    position = deviceLocation.value
                )
            ) { marker ->
                Text(marker.title ?: "You", color = Color.Red)
            }
        }
    }

}

/**
 * Prompts the user for permission to use the device location.
 */
private fun getLocationPermission(context: Context) {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
    if (ContextCompat.checkSelfPermission(
            (context as MainActivity).applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        == PackageManager.PERMISSION_GRANTED
    ) {
        locationPermissionGranted.value = true
    } else {
        ActivityCompat.requestPermissions(
            context as MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
        )
    }
}