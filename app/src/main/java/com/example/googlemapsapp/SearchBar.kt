package com.example.googlemapsapp

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar() {
    var searchQuery by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        maxLines = 1,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        value = searchQuery,
        onValueChange = { searchQuery = it },
        label = {
            Text(text = "Search for area,street name..")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "")
        },
        singleLine = true,
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