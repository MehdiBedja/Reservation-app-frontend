package com.example.reservation_app_frontend.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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

}


@Composable
fun ParkingAppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destination.SignUp.route
    ) {
        composable(Destination.LogIn.route) {
            LogInScreen(navController = navController)
        }
        composable(Destination.SignUp.route) {
//            SignUpScreen(navController = navController, viewModel= viewModel)
            SignUpScreen(navController = navController)
        }
        composable(Destination.ListOfParkings.route) {
//            ShowParkingList()
        }
    }
}
