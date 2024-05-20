package com.example.reservation_app_frontend.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.reservation_app_frontend.screen.parking.ShowParkingList
import com.example.reservation_app_frontend.screen.user.LogInScreen
import com.example.reservation_app_frontend.screen.user.SignUpScreen
import com.example.reservation_app_frontend.viewModel.user.AccountViewModel
import com.example.reservation_app_frontend.viewModel.user.LoginViewModel

sealed class Destination(val route : String) {
    object LogIn:Destination ("LogIn")
    object SignUp:Destination("SignUp")
    object ListOfParkings:Destination("List_Of_Parkings")

    object ShowReservationList:Destination("My Reservations")


}


@Composable
fun ParkingAppNavigation(navController: NavHostController) {

    var currentDestination by remember { mutableStateOf("LogIn") }

    NavHost(
        navController = navController,
        startDestination = Destination.LogIn.route
    ) {
        composable(Destination.LogIn.route) {
            LogInScreen(navController = navController , onLoginSuccess = {
                currentDestination = "List_Of_Parkings"
                navController.navigate("List_Of_Parkings")


            })
        }
        composable(Destination.SignUp.route) {
//            SignUpScreen(navController = navController, viewModel= viewModel)
            SignUpScreen(navController = navController)
        }
        composable(Destination.ListOfParkings.route) {
        }
    }
}

@Composable
fun test(){
Text(text = "hello")
}