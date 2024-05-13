package com.example.reservation_app_frontend.screen.navigation

import AddReservationScreen
import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.reservation_app_frontend.screen.parking.OneParking
import com.example.reservation_app_frontend.screen.parking.ShowParkingList
import com.example.reservation_app_frontend.screen.reservation.OneReservation
import com.example.reservation_app_frontend.screen.reservation.ShowReservationList
import com.example.reservation_app_frontend.screen.user.ShowProfile
import com.example.reservation_app_frontend.viewModel.parking.getParkingsViewModel
import com.example.reservation_app_frontend.viewModel.reservation.AddReservationViewModel
import com.example.reservation_app_frontend.viewModel.reservation.getMyReservationsViewModel
import getAllReservationModel

@ExperimentalFoundationApi
@Composable
fun MainScreen(reservationsViewModel: getMyReservationsViewModel ,reservationViewModel: AddReservationViewModel ,
               reservationModel: getAllReservationModel, parkingViewModel: getParkingsViewModel ,
               navController: NavHostController
                , context: Context) {
    val items = listOf(
        Destination.ShowParkingList,
        Destination.ShowReservationList,
        Destination.ShowProfile,
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
                            // You can set different icons for each screen here
                            // For example, Icons.Filled.Favorite for Ecran1
                            Icon(Icons.Filled.Home, contentDescription = null)
                        },
                        label = {
                            // You can set different labels for each screen here
                            Text(text = des.route)
                        },
                        selected = selected,
                        onClick = {
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
        NavHost(navController, startDestination = Destination.ShowParkingList.route, Modifier.padding(innerPadding)) {
            composable(Destination.ShowParkingList.route) { ShowParkingList(parkingViewModel , navController) }
            composable(Destination.OneParking.route) {navBack ->
                val id = navBack?.arguments?.getString("userId")?.toInt()
                OneParking(id)
            }

            composable(Destination.ShowReservationList.route) { ShowReservationList(reservationsViewModel , navController ) }
            composable(Destination.oneReservation.route) {navBack ->
                val id = navBack?.arguments?.getString("reservationId")?.toInt()
                OneReservation(id)
            }

            composable(Destination.ShowProfile.route) { ShowProfile() }
            composable(Destination.AddReservationScreen.route) { AddReservationScreen(reservationModel ,reservationViewModel,context, navController ) }

        }
    }
}