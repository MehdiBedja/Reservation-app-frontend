package com.example.reservation_app_frontend

import AddReservationScreen
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.reservation_app_frontend.endpoint.parking.parkingEndpoint
import com.example.reservation_app_frontend.endpoint.reservation.ReservationEndpoint

import com.example.reservation_app_frontend.repository.parking.ParkingRepository
import com.example.reservation_app_frontend.repository.reservation.ReservationRepository
import com.example.reservation_app_frontend.screen.navigation.MainScreen
import com.example.reservation_app_frontend.screen.parking.ShowParkingList
import com.example.reservation_app_frontend.screen.reservation.ShowReservationList
import com.example.reservation_app_frontend.ui.theme.Reservation_app_frontendTheme
import com.example.reservation_app_frontend.viewModel.parking.getParkingsViewModel
import com.example.reservation_app_frontend.viewModel.reservation.AddReservationViewModel
import com.example.reservation_app_frontend.viewModel.reservation.getMyReservationsViewModel
import getAllReservationModel

class MainActivity : ComponentActivity() {
    @SuppressLint("SuspiciousIndentation")
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Reservation_app_frontendTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Create an instance of your ViewModel and fetch parkings outside the setContent block





                  //  val endpoint = ReservationEndpoint.createEndpoint()
                    //val reservationRepository by lazy { ReservationRepository(endpoint) }
                    //   reservationviewModel.fetchReservations()

                    val endpoint = parkingEndpoint.createEndpoint()
                    val parkingRepository by lazy { ParkingRepository(endpoint) }



                    val parkingViewModel = getParkingsViewModel.Factory(parkingRepository).create(getParkingsViewModel::class.java)
                    parkingViewModel.fetchParkings()

                    val reservationViewModelAll = getAllReservationModel.Factory(parkingRepository).create(getAllReservationModel::class.java)
                    reservationViewModelAll.fetchParkings()

                    val endpoint2 = ReservationEndpoint.createEndpoint()
                    val reservationRepository by lazy { ReservationRepository(endpoint2) }
                    val addReservationViewModel = AddReservationViewModel.Factory(reservationRepository).create(AddReservationViewModel::class.java)
                    val reservationviewModel = getMyReservationsViewModel.Factory(reservationRepository).create(getMyReservationsViewModel::class.java)

                    reservationviewModel.fetchReservations()



                    // ShowParkingList(parkingViewModel)                }
                    // AddReservationScreen(reservationViewModelAll ,addReservationViewModel , this )
                    // ShowReservationList(reservationviewModel )  }




                val navController = rememberNavController()
                    MainScreen(reservationviewModel ,addReservationViewModel , reservationViewModelAll , parkingViewModel, navController , this)
                }


            }
        }
    }
}

