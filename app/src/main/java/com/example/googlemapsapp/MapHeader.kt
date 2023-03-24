package com.example.googlemapsapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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