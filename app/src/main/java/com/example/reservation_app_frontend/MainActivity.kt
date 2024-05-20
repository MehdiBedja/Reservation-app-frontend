package com.example.reservation_app_frontend

import AddReservationScreen
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.reservation_app_frontend.navigation.ParkingAppNavigation
import com.example.reservation_app_frontend.network.initializeUsername

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


class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Reservation_app_frontendTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {



//                    val endpoint = userEndpoint.createEndpoint()
//                    val authRepository = AuthRepository(endpoint)
////                    val accountViewModel = AccountViewModel.Factory(authRepository).create(AccountViewModel::class.java)
//                    val accountViewModel = AccountViewModel.getInstance(authRepository)

////////////////////////////////////////////main area, other areas are tests/////////////////////////////////////////////////////
                    val navController = rememberNavController()
//                    ParkingAppNavigation(navController, accountViewModel)
                    ParkingAppNavigation(navController)
/////////////////////////////////////////////////////////////////////////////////////////////////

                    //   Create an instance of your ViewModel and fetch parkings outside the setContent block
//                    val endpoint = parkingEndpoint.createEndpoint()
//                    val parkingRepository by lazy { ParkingRepository(endpoint) }
//                    val parkingViewModel = getParkingsViewModel.Factory(parkingRepository).create(getParkingsViewModel::class.java)
//                    parkingViewModel.fetchParkings()
//                    ShowParkingList(parkingViewModel)


                    // SINGNUP OLD METHODE WITHOUT NAVIGATION
//                    val endpoint = userEndpoint.createEndpoint()
//                    val authRepository = AuthRepository(endpoint)
//                    val accountViewModel = AccountViewModel.Factory(authRepository).create(AccountViewModel::class.java)
//
//                    // Call the signUpUser function with test data
//                    SignUpScreenOld(accountViewModel)




//                    accountViewModel.signUpUser(
//                        email = "test1@example.com",
//                        username = "testuser1",
//                        lastName = "Doe",
//                        firstName = "John",
//                        phoneNumber = "1234567890",
//                        password = "test123"
//                    )
//
//                    // Display loading indicator and error message based on viewModel state
//                    if (accountViewModel.loading.value) {
//                        LinearProgressIndicator(
//                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
//
//                        )
//                    } else {
//                        accountViewModel.error.value?.let { errorMessage ->
//                            Text(
//                                text = errorMessage,
//                                color = Color.Red,
//                                modifier = Modifier.padding(16.dp)
//                            )
//                        }
//                    }


                    //

                    //LOGNI
//                    val endpoint = userEndpoint.createEndpoint()
//                    val authRepository = AuthRepository(endpoint)
//                    val loginViewModel = LoginViewModel.Factory(authRepository).create(LoginViewModel::class.java)
//
//                    LogInScreen(loginViewModel)


                    //
//                    val endpoint = ReservationEndpoint.createEndpoint()
//                    val reservationRepository by lazy { ReservationRepository(endpoint) }
//                    val reservationviewModel = getMyReservationsViewModel.Factory(reservationRepository).create(getMyReservationsViewModel::class.java)
//                    reservationviewModel.fetchReservations()
//                    ShowReservationList(reservationviewModel)


                }






            }
        }
    }
}



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


                    initializeUsername(this)



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

