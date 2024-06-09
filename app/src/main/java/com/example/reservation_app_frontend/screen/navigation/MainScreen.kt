package com.example.reservation_app_frontend.screen.navigation

import AddReservationScreen
import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.reservation_app_frontend.endpoint.parking.parkingEndpoint
import com.example.reservation_app_frontend.endpoint.reservation.ReservationEndpoint
import com.example.reservation_app_frontend.network.Globals
import com.example.reservation_app_frontend.repository.parking.ParkingRepository
import com.example.reservation_app_frontend.repository.reservation.ReservationRepository
import com.example.reservation_app_frontend.screen.parking.OneParking
import com.example.reservation_app_frontend.screen.parking.ShowParkingList
import com.example.reservation_app_frontend.screen.reservation.OneReservation
import com.example.reservation_app_frontend.screen.reservation.ShowReservationList
import com.example.reservation_app_frontend.screen.user.LogInScreen
import com.example.reservation_app_frontend.screen.user.LogoutButton
import com.example.reservation_app_frontend.screen.user.ShowProfile
import com.example.reservation_app_frontend.screen.user.SignUpScreen
import com.example.reservation_app_frontend.screen.user.UserProfileScreen
import com.example.reservation_app_frontend.viewModel.parking.getParkingsViewModel
import com.example.reservation_app_frontend.viewModel.reservation.AddReservationViewModel
import com.example.reservation_app_frontend.viewModel.reservation.getMyReservationsViewModel
import com.example.reservation_app_frontend.viewModel.user.LoginViewModel
import getAllReservationModel

@ExperimentalFoundationApi
@Composable
fun MainScreen(
    navController: NavHostController
    , context: Context,
    startDestination: String,
    userViewModel: LoginViewModel ,
    //   appDatabase: AppDatabase
)
{

    val endpoint = parkingEndpoint.createEndpoint()
    val parkingRepository by lazy { ParkingRepository(endpoint) }
    val parkingViewModel = getParkingsViewModel.Factory(parkingRepository).create(getParkingsViewModel::class.java)
    parkingViewModel.fetchParkings()


    val reservationViewModelAll = getAllReservationModel.Factory(parkingRepository).create(getAllReservationModel::class.java)
    reservationViewModelAll.fetchParkings()





    val endpoint2 = ReservationEndpoint.createEndpoint()
    val reservationRepository by lazy { ReservationRepository(endpoint2
        //    , appDatabase
    ) }
    val addReservationViewModel = AddReservationViewModel.Factory(reservationRepository).create(AddReservationViewModel::class.java)
    val reservationviewModel = getMyReservationsViewModel.Factory(reservationRepository).create(getMyReservationsViewModel::class.java)

    reservationviewModel.fetchReservations()

    val items = listOf(
        Destination.ShowParkingList,
        Destination.ShowReservationList,
        Destination.UserProfileScreen,
        Destination.AddReservationScreen
    )

    Scaffold(
        bottomBar = {
            NavigationBar  {
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.value?.destination

                items.forEach { des ->
                    val selected = currentDestination?.hierarchy?.any { it.route == des.route } == true
                    NavigationBarItem(
                        icon = {
                            when (des) {
                                Destination.ShowParkingList -> Icon(Icons.Filled.Person, contentDescription = null) // Replace with appropriate icon
                                Destination.ShowReservationList -> Icon(Icons.Filled.List, contentDescription = null) // Replace with appropriate icon
                                Destination.UserProfileScreen -> Icon(Icons.Filled.Person, contentDescription = null) // Replace with appropriate icon
                                Destination.AddReservationScreen -> Icon(Icons.Filled.Create, contentDescription = null) // Replace with appropriate icon
                                else -> {}
                            }
                        },
                        selected = selected,
                        onClick = {
                            if (des == Destination.ShowReservationList) {
                                reservationviewModel.fetchReservations() }
                            if (des == Destination.UserProfileScreen) {
                                Globals.savedUsername?.let { userViewModel.getUser(it) }
                            }

                            navController.navigate(des.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }



                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = startDestination, Modifier.padding(innerPadding)) {


            composable(Destination.LogoutButton.route) {
                LogoutButton(navController = navController , userViewModel)

            }


            composable(Destination.SignUp.route) {
                SignUpScreen(navController = navController)
            }

            composable(Destination.LogIn.route) {
                LogInScreen(navController = navController, userViewModel)
            }



            composable(Destination.ShowParkingList.route) { ShowParkingList(parkingViewModel , navController)  }
            composable(Destination.OneParking.route) {navBack ->
                val id = navBack?.arguments?.getString("userId")?.toInt()
                OneParking(id)
            }

            composable(Destination.ShowReservationList.route) { ShowReservationList(reservationviewModel , navController ) }
            composable(Destination.oneReservation.route) {navBack ->
                val id = navBack?.arguments?.getString("reservationId")?.toInt()
                OneReservation(id
                    //  , appDatabase
                )
            }

            composable(Destination.UserProfileScreen.route) { UserProfileScreen(userViewModel,navController) }

            composable(Destination.AddReservationScreen.route) { AddReservationScreen(reservationViewModelAll ,addReservationViewModel, context,reservationviewModel , navController ) }

        }
    }
}