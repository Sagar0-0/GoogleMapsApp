package com.example.googlemapsapp

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.googlemapsapp.utils.AUTOCOMPLETE_REQUEST_CODE
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

@Composable
fun SearchBar(getPlace: (Place)->Unit) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val fields = listOf(Place.Field.ID, Place.Field.NAME,Place.Field.ADDRESS,Place.Field.LAT_LNG)
    // Create the autocomplete intent.
    val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
        .build(context)

    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Handle the activity result here
            result.data?.let {
                val place = Autocomplete.getPlaceFromIntent(it)
                getPlace(place)
                searchQuery = place.name as String
            }
        }
    }


    OutlinedTextField(
        maxLines = 1,
        singleLine = true,
        readOnly = false,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        value = searchQuery,
        onValueChange = {},
        placeholder = {
            Text(text = "Search for area,street name..")
        },
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Release) {
                            launcher.launch(intent)
//                            (context as MainActivity).startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
                            focusManager.clearFocus()
                        }
                    }
                }
            },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "")
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.DarkGray,
            backgroundColor = Color.White,
            cursorColor = Color.DarkGray,
            focusedBorderColor = Color.Black,
            leadingIconColor = Color.Red,
            focusedLabelColor = Color.DarkGray,
            unfocusedLabelColor = Color.DarkGray
        )
    )
}