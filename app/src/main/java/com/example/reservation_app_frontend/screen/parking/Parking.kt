package com.example.reservation_app_frontend.screen.parking

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.reservation_app_frontend.data.parking.Parking
import com.example.reservation_app_frontend.endpoint.parking.parkingEndpoint
import com.example.reservation_app_frontend.network.url
import com.example.reservation_app_frontend.repository.parking.ParkingRepository
import com.example.reservation_app_frontend.viewModel.parking.getParkingsViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun OneParking(parkingId: Int?) {
    val endpoint = parkingEndpoint.createEndpoint()
    val parkingRepository by lazy { ParkingRepository(endpoint) }
    val parkingViewModel = getParkingsViewModel.Factory(parkingRepository).create(getParkingsViewModel::class.java)



    val parking by remember { parkingViewModel.parking }
    val isLoading by remember { parkingViewModel.loading }

    LaunchedEffect(Unit) {
        parkingViewModel.getParkingById(parkingId) // Start loading
    }

    if (isLoading) {
        AddProgress1(parkingViewModel = parkingViewModel, parkingId)
    } else {
        parking?.let {
            Log.d("OneParking", "Parking not null: $it")
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val url2 = "static/images/"
                    AsyncImage(
                        model = url + url2 + it.image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp)
                    )

                    Text(text = "${it.name}", style = MaterialTheme.typography.headlineLarge)

                    it.description?.let { description ->
                        Text(text = description, style = MaterialTheme.typography.labelMedium)
                    }

                    Text(text = "Commune: ${it.commune_name}", style = MaterialTheme.typography.labelMedium)
                 //   Text(text = "Commune: ${it.latitude}", style = MaterialTheme.typography.labelMedium)
                   // Text(text = "Commune: ${it.longitude}", style = MaterialTheme.typography.labelMedium)
                    it.latitude?.let { it1 -> it.longitude?.let { it2 -> MapScreen(latitude = it1, longitude = it2 , it.commune_name) } }


                    // You can add more details or customize the layout as needed
                }
            }
        } ?: run {
            Log.e("OneParking", "Parking is null")
            // Handle null parking scenario, maybe show an error message or navigate back
        }
    }
}

@Composable
fun AddProgress1(parkingViewModel: getParkingsViewModel, parkingId: Int?) {
    LaunchedEffect(Unit) {
        parkingViewModel.getParkingById(parkingId) // Start loading
    }

    val isLoading = parkingViewModel.loading.value

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun MapScreen(latitude: Double, longitude: Double , commune : String , ) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(latitude, longitude), 15f)
    }

    var uiSettings by remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
    }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.SATELLITE))
    }

    GoogleMap(
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings
    ) {
        MarkerInfoWindow(
            state = MarkerState(position = LatLng(latitude, longitude)),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .border(
                        BorderStroke(1.dp, Color.Black),
                        RoundedCornerShape(10)
                    )
                    .clip(RoundedCornerShape(10))
                    .background(Color.Blue)
                    .padding(20.dp)
            ) {
                Text(commune, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}
