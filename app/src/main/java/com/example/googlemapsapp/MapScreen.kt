package com.example.googlemapsapp

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
    val fusedLocationProviderClient =
        remember { LocationServices.getFusedLocationProviderClient(context) }

    var lastKnownLocation by remember {
        mutableStateOf<Location?>(null)
    }

    var markerLatLng by remember {
        mutableStateOf(LatLng(0.0, 0.0))
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerLatLng, 18f)
    }


    var bottomSheetText by remember {
        mutableStateOf("")
    }

    val getCurrentLocation: () -> Unit = {
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
                    bottomSheetText =
                        "${address.featureName}, ${address.subLocality}, ${address.locality}, ${address.adminArea}"
                }
                markerLatLng = LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
                cameraPositionState.position = CameraPosition.fromLatLngZoom(markerLatLng, 18f)
            } else {
                Log.d(TAG, "Current location is null. Using defaults.")
                Log.e(TAG, "Exception: %s", task.exception)
            }
        }
    }

    if (markerLatLng.latitude == 0.0 && markerLatLng.latitude == 0.0) {
        getCurrentLocation()
    }

    var bottomSheetValue by rememberSaveable {
        mutableStateOf(BottomSheetValue.Collapsed)
    }

    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(initialValue = bottomSheetValue)
    )
    val scope = rememberCoroutineScope()
    val openSheet = {
        bottomSheetValue = BottomSheetValue.Expanded
        scope.launch {
            sheetState.bottomSheetState.expand()
        }
    }

    val closeSheet = {
        bottomSheetValue = BottomSheetValue.Collapsed
        scope.launch {
            sheetState.bottomSheetState.collapse()
        }
    }

    BottomSheetScaffold(
        sheetElevation = 10.dp,
        sheetGesturesEnabled = false,
        scaffoldState = sheetState,
        topBar = {
            MapHeader()
        },
        sheetPeekHeight = 150.dp,
        sheetBackgroundColor = if (sheetState.bottomSheetState.isCollapsed) Color.Transparent else Color.Black.copy(
            0.2f
        ),
        sheetContent = {
            MyBottomSheet(sheetScaffoldState = sheetState,
                bottomSheetText,
                onButtonClick = { openSheet() },
                onCollapse = { closeSheet() })
        }) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
        ) {
            GoogleMap(
                cameraPositionState = cameraPositionState,
                onMapLongClick = {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(
                        it.latitude,
                        it.longitude,
                        1
                    )

                    if (addresses != null && addresses.isNotEmpty()) {
                        val address = addresses[0]
                        bottomSheetText =
                            "${address.featureName}, ${address.subLocality}, ${address.locality}, ${address.adminArea}"
                    }
                    markerLatLng = it
                }
            ) {
                MarkerInfoWindowContent(
                    state = MarkerState(
                        position = markerLatLng
                    )
                ) { marker ->
                    Text(marker.title ?: "You", color = Color.Red)
                }
            }

            SearchBar {
                bottomSheetText = it.name as String
                markerLatLng = it.latLng as LatLng
                cameraPositionState.position = CameraPosition.fromLatLngZoom(markerLatLng, 18f)
            }

            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { getCurrentLocation() }
                    .background(Color.Red)
                    .padding(10.dp)
                    .align(Alignment.BottomCenter),
                text = "Use current Location",
                color = Color.Black,
                fontSize = 18.sp
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyBottomSheet(
    sheetScaffoldState: BottomSheetScaffoldState,
    locationName: String,
    onButtonClick: () -> Unit,
    onCollapse: () -> Unit,
) {
    Box(
        Modifier
            .fillMaxSize()
    ) {
        if (sheetScaffoldState.bottomSheetState.isCollapsed) {
            LazyColumn(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(Color.DarkGray)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
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
                }
                item {
                    Button(
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(15.dp)),
                        onClick = onButtonClick
                    ) {
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
            }

        } else {
            FillAddressDetails(onClick = onCollapse, locationName = locationName)
        }
    }
}