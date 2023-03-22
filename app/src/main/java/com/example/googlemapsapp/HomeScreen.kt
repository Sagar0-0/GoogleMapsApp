package com.example.googlemapsapp

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.location.Location
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.googlemapsapp.utils.M_MAX_ENTRIES
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.maps.android.compose.*


//private var locationPermissionGranted = mutableStateOf(false)
//private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1234

@SuppressLint("MissingPermission")
@Composable
fun HomeScreen() {
    val context = LocalContext.current
//    val placesClient = Places.createClient(context)
//
//    var likelyPlaceNames by remember {
//        mutableStateOf<Array<String?>>(arrayOfNulls(0))
//    }
//    var likelyPlaceAddresses by remember {
//        mutableStateOf<Array<String?>>(arrayOfNulls(0))
//    }
//    var likelyPlaceAttributions by remember {
//        mutableStateOf<Array<List<*>?>>(arrayOfNulls(0))
//    }
//    var likelyPlaceLatLngs by remember {
//        mutableStateOf<Array<LatLng?>>(arrayOfNulls(0))
//    }
//    var count by remember {
//        mutableStateOf(0)
//    }

    val fusedLocationProviderClient =
        remember { LocationServices.getFusedLocationProviderClient(context) }

    var lastKnownLocation by remember {
        mutableStateOf<Location?>(null)
    }

    var deviceLatLng by remember {
        mutableStateOf(LatLng(0.0, 0.0))
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(deviceLatLng, 18f)
    }

    val locationResult = fusedLocationProviderClient.lastLocation
    locationResult.addOnCompleteListener(context as MainActivity) { task ->
        if (task.isSuccessful) {
            // Set the map's camera position to the current location of the device.
            lastKnownLocation = task.result
            deviceLatLng = LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
            cameraPositionState.position = CameraPosition.fromLatLngZoom(deviceLatLng, 18f)
        } else {
            Log.d(TAG, "Current location is null. Using defaults.")
            Log.e(TAG, "Exception: %s", task.exception)
        }
    }

    // Use fields to define the data types to return.
//    val placeFields = listOf(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
//
//    // Use the builder to create a FindCurrentPlaceRequest.
//    val request = FindCurrentPlaceRequest.newInstance(placeFields)
//
//    // Get the likely places - that is, the businesses and other points of interest that
//    // are the best match for the device's current location.
//    val placeResult = placesClient.findCurrentPlace(request)
//    placeResult.addOnCompleteListener { task ->
//        if (task.isSuccessful && task.result != null) {
//            val likelyPlaces = task.result
//
//            // Set the count, handling cases where less than 5 entries are returned.
//            count =
//                if (likelyPlaces != null && likelyPlaces.placeLikelihoods.size < M_MAX_ENTRIES) {
//                    likelyPlaces.placeLikelihoods.size
//                } else {
//                    M_MAX_ENTRIES
//                }
//            var i = 0
//            likelyPlaceNames = arrayOfNulls(count)
//            likelyPlaceAddresses = arrayOfNulls(count)
//            likelyPlaceAttributions = arrayOfNulls<List<*>?>(count)
//            likelyPlaceLatLngs = arrayOfNulls(count)
//            for (placeLikelihood in likelyPlaces?.placeLikelihoods ?: emptyList()) {
//                // Build a list of likely places to show the user.
//                likelyPlaceNames[i] = placeLikelihood.place.name
//                likelyPlaceAddresses[i] = placeLikelihood.place.address
//                likelyPlaceAttributions[i] = placeLikelihood.place.attributions
//                likelyPlaceLatLngs[i] = placeLikelihood.place.latLng
//                i++
//                if (i > count - 1) {
//                    break
//                }
//            }
//        } else {
//            Log.e(TAG, "Exception: %s", task.exception)
//        }
//    }

    Column(Modifier.fillMaxSize()) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .background(Color.Green)
//                .padding(10.dp)
//                .fillMaxWidth()
//                .height(34.dp)
//        ) {
//            Icon(
//                modifier = Modifier.size(24.dp),
//                imageVector = Icons.Default.ArrowBack,
//                contentDescription = ""
//            )
//            Spacer(modifier = Modifier.width(6.dp))
//            Text(text = "Location", modifier = Modifier.weight(1f), fontSize = 24.sp)
//        }

        GoogleMap(
//            modifier = Modifier.weight(0.5f),
            cameraPositionState = cameraPositionState
        ) {
            MarkerInfoWindowContent(
                state = MarkerState(
                    position = deviceLatLng
                )
            ) { marker ->
                Text(marker.title ?: "You", color = Color.Red)
            }
//            for (i in 0 until count) {
//                MarkerInfoWindowContent(
//                    state = MarkerState(likelyPlaceLatLngs[i]!!)
//                ) {
//                    Text(likelyPlaceNames[i]!!, color = Color.Red)
//                }
//            }
        }

//        LazyColumn(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.LightGray)
//                .weight(0.5f)
//        ) {
//            if (count == 0) {
//                item {
//                    CircularProgressIndicator()
//                }
//            }
//            items(count) { i ->
//                Text(
//                    modifier = Modifier.padding(5.dp),
//                    text = likelyPlaceNames[i]!!, fontSize = 24.sp
//                )
//                Spacer(
//                    modifier = Modifier
//                        .height(3.dp)
//                        .background(Color.Black)
//                )
//            }
//        }
    }
}