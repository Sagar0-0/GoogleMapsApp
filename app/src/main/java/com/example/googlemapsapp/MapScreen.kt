package com.example.googlemapsapp

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch
import java.util.*


//private var locationPermissionGranted = mutableStateOf(false)
//private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1234

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("MissingPermission")
@Composable
fun MapScreen() {
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

    var cityName by remember {
        mutableStateOf("")
    }
    var stateName by remember {
        mutableStateOf("")
    }

    val locationResult = fusedLocationProviderClient.lastLocation
    locationResult.addOnCompleteListener(context as MainActivity) { task ->
        if (task.isSuccessful) {
            // Set the map's camera position to the current location of the device.
            lastKnownLocation = task.result
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(
                lastKnownLocation!!.latitude,
                lastKnownLocation!!.longitude,
                1
            )

            if (addresses != null && addresses.isNotEmpty()) {
                val address = addresses[0]
                cityName = address.locality
                stateName = address.adminArea
            }
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

    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        sheetElevation = 10.dp,
        sheetGesturesEnabled = false,
        scaffoldState = sheetState,
        topBar = {
            MapHeader()
        },
        sheetShape = RoundedCornerShape(20.dp),
        sheetPeekHeight = 150.dp,
        sheetBackgroundColor = if (sheetState.bottomSheetState.isCollapsed) Color.Transparent else Color.Black.copy(
            0.2f
        ),
        sheetContent = {
            MyBottomSheet(sheetScaffoldState = sheetState,"$cityName , $stateName") {
                scope.launch { sheetState.bottomSheetState.expand() }
            }
        }) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
        ) {
            GoogleMap(
                cameraPositionState = cameraPositionState
            ) {
                MarkerInfoWindowContent(
                    state = MarkerState(
                        position = deviceLatLng
                    )
                ) { marker ->
                    Text(marker.title ?: "You", color = Color.Red)
                }
            }
            SearchBar()
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyBottomSheet(sheetScaffoldState: BottomSheetScaffoldState,locationName: String, onButtonClick: () -> Unit) {
    if (sheetScaffoldState.bottomSheetState.isCollapsed) {
        Column(
            modifier = Modifier
                .heightIn(min = 150.dp, max = 500.dp)
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .size(30.dp),
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color.Red
                )
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = locationName,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp)),
                onClick = {
                    onButtonClick()
                }) {
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(10.dp),
                    text = "Enter complete address",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        AddressDetailsScreen()
    }
}

@Composable
fun AddressDetailsScreen() {
    Text(text = "Enter Address Details...")
}

@Composable
fun MapHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(15.dp),
            imageVector = Icons.Default.ArrowBack,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = "Choose Location", fontSize = 24.sp, modifier = Modifier.weight(1f))
    }
}