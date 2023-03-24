package com.example.googlemapsapp

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
fun SearchBar(place: Place?) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val fields = listOf(Place.Field.ID, Place.Field.NAME,Place.Field.ADDRESS,Place.Field.LAT_LNG)
    // Create the autocomplete intent.
    val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
        .build(context)

    val searchQuery by rememberSaveable {
        mutableStateOf(if(place!=null) place.name else "")
    }
    OutlinedTextField(
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
                            (context as MainActivity).startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
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