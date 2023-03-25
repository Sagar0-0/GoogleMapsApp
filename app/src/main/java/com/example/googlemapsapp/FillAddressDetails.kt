package com.example.googlemapsapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FillAddressDetails(locationName: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "",
            modifier = Modifier
                .clickable { onClick() }
                .padding(20.dp)
                .size(30.dp),
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color.DarkGray)
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            item {
                Row(
                    Modifier
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
                Text(text = "Save address as*", Modifier.padding(bottom = 8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    val selectedAddressType = rememberSaveable {
                        mutableStateOf("Home")
                    }
                    Text(modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (selectedAddressType.value == "Home") Color.Red else Color.Transparent)
                        .clickable { selectedAddressType.value = "Home" }
                        .padding(8.dp), text = "Home")
                    Text(modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (selectedAddressType.value == "Work") Color.Red else Color.Transparent)
                        .clickable { selectedAddressType.value = "Work" }
                        .padding(8.dp), text = "Work")
                    Text(modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (selectedAddressType.value == "Hotel") Color.Red else Color.Transparent)
                        .clickable { selectedAddressType.value = "Hotel" }
                        .padding(8.dp), text = "Hotel")
                    Text(modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (selectedAddressType.value == "Other") Color.Red else Color.Transparent)
                        .clickable { selectedAddressType.value = "Other" }
                        .padding(8.dp), text = "Other")
                }
            }
            item {
                val houseNo = rememberSaveable {
                    mutableStateOf("")
                }
                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    value = houseNo.value,
                    onValueChange = {
                        houseNo.value = it
                    },
                    label = {
                        Text(text = "House Number*")
                    }
                )
            }
            item {
                val floor = rememberSaveable {
                    mutableStateOf("")
                }
                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    value = floor.value,
                    onValueChange = {
                        floor.value = it
                    },
                    label = {
                        Text(text = "Floor")
                    }
                )
            }
            item {
                val tower = rememberSaveable {
                    mutableStateOf("")
                }
                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    value = tower.value,
                    onValueChange = {
                        tower.value = it
                    },
                    label = {
                        Text(text = "Tower / Block*")
                    }
                )
            }
            item {
                val nearby = rememberSaveable {
                    mutableStateOf("")
                }
                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    value = nearby.value,
                    onValueChange = {
                        nearby.value = it
                    },
                    label = {
                        Text(text = "Nearby landmark(optional)")
                    }
                )
            }
            item {
                Button(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp)),
                    onClick = { }
                ) {
                    Text(
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(10.dp),
                        text = "Save address",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}