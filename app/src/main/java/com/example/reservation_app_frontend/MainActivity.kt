package com.example.reservation_app_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.reservation_app_frontend.endpoint.parking.parkingEndpoint.Companion.createEndpoint
import com.example.reservation_app_frontend.endpoint.reservation.ReservationEndpoint
import com.example.reservation_app_frontend.endpoint.reservation.ReservationEndpoint.Companion.createEndpoint

import com.example.reservation_app_frontend.repository.parking.ParkingRepository
import com.example.reservation_app_frontend.repository.reservation.ReservationRepository
import com.example.reservation_app_frontend.screen.parking.ShowParkingList
import com.example.reservation_app_frontend.screen.reservation.ShowReservationList
import com.example.reservation_app_frontend.ui.theme.Reservation_app_frontendTheme
import com.example.reservation_app_frontend.viewModel.parking.getParkingsViewModel
import com.example.reservation_app_frontend.viewModel.reservation.getMyReservationsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Reservation_app_frontendTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Create an instance of your ViewModel and fetch parkings outside the setContent block
                    //val endpoint = createEndpoint()
                    //val parkingRepository by lazy { ParkingRepository(endpoint) }
                    //val parkingViewModel = getParkingsViewModel.Factory(parkingRepository).create(getParkingsViewModel::class.java)
                    //parkingViewModel.fetchParkings()
                    //ShowParkingList(parkingViewModel)                }



                    val endpoint = ReservationEndpoint.createEndpoint()
                    val reservationRepository by lazy { ReservationRepository(endpoint) }
                    val reservationviewModel = getMyReservationsViewModel.Factory(reservationRepository).create(getMyReservationsViewModel::class.java)
                    reservationviewModel.fetchReservations()
                    ShowReservationList(reservationviewModel)  }



            }
        }
    }
}

