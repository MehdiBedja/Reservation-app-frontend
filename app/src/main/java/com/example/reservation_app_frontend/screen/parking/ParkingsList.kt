package com.example.reservation_app_frontend.screen.parking

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.reservation_app_frontend.network.url
import com.example.reservation_app_frontend.screen.navigation.Destination
import com.example.reservation_app_frontend.viewModel.parking.getParkingsViewModel


@Composable
fun ShowParkingList(parkingViewModel: getParkingsViewModel , navController: NavController) {

    AddProgress(parkingViewModel)
    
    

    LazyColumn {
        items(parkingViewModel.parkings) { parking ->
            Row(
                modifier = Modifier
                    .padding(6.dp)
                    .background(Color(0xFF84C6E4))
                    .clickable {
                        parkingViewModel.getParkingById(parking.id)

                        navController.navigate(Destination.OneParking.createRoute(parking.id)) {
                        }
                    }

            )
                        {
val url2 = "static/images/"
                AsyncImage(
                    model = url+ url2+parking.image,
                    contentDescription = null ,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp),
                )


                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = parking.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = parking.description,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }

}

@Composable
fun AddProgress(parkingViewModel: getParkingsViewModel) {
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