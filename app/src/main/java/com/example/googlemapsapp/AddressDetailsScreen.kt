package com.example.googlemapsapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddressDetailsScreen(onClick: () -> Unit) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(text = "Enter Address Details...")
            Button(onClick = onClick) {
                Text(text = "Collapse")
            }
        }
    }
}