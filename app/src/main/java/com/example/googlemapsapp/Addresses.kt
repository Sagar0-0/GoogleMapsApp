package com.example.googlemapsapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddressesScreen() {
    Column(Modifier.fillMaxSize()) {

        TopAppBar(
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                modifier = Modifier.padding(15.dp),
                imageVector = Icons.Default.ArrowBack,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "Addresses", fontSize = 24.sp, modifier = Modifier.weight(1f))
        }

        SearchBar()

        Row(
            Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(24.dp),
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                tint = Color.Red
            )
            Text(
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = "Use my current location",
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                color = Color.Red
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "",
                tint = Color.Red
            )
        }

        Row(
            Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(24.dp),
                imageVector = Icons.Default.Add,
                contentDescription = "Add Address",
                tint = Color.Red
            )
            Text(
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = "Add Address",
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                color = Color.Red
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "",
                tint = Color.Red
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )

        Text(
            modifier = Modifier.padding(10.dp),
            text = "SAVED ADDRESSES",
            fontWeight = FontWeight.Bold
        )
        LazyColumn {

        }
    }
}

@Composable
fun AddressItem(
    icon: ImageVector,
    name: String,
    address: String,
    contactNo: String,
    onClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onShareClick: () -> Unit
) {

}
