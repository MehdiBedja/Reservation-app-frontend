package com.example.reservation_app_frontend.screen.navigation

sealed class Destination(val route : String) {
    object ShowParkingList:Destination ("Parkings List")
    object OneParking:Destination ("details/{userId}") {
        fun createRoute(userId:Int) = "details/$userId"
    }
    object Ecran3:Destination ("ecran 3")
    object ShowReservationList:Destination ("My Reservations")
    object AddReservationScreen:Destination ("add reservation")
    object oneReservation:Destination ("d/{reservationId}")  {
        fun createRoute1(reservationId: Int?) = "d/$reservationId"
    }
    object UserProfileScreen:Destination ("My Profile")

    object LogoutButton:Destination ("Logout")


    object LogIn:Destination("LogIn")
    object SignUp:Destination("SignUp")



    object Profile:Destination("profile")
    object Details:Destination("details/{userId}") {
        fun createRoute(userId:Int) = "details/$userId"
    }

}