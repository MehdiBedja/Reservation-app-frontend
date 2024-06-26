package com.example.reservation_app_frontend.data.reservation

import com.example.reservation_app_frontend.data.parking.ParkingPlace
import com.example.reservation_app_frontend.data.user.CustomUser

data class Reservation(
    val id: Int,
    val user: CustomUser,
    val parking_place: ParkingPlace?, // Change the property name to match JSON ("parking_place")
    val reservation_date: String?, // Change to match JSON ("reservation_date")
    val entry_datetime: String?, // Change to match JSON ("entry_datetime")
    val exit_datetime: String?, // Change to match JSON ("exit_datetime")
    val payment_status: String?, // Change to match JSON ("payment_status")
    val reservation_code: String? // Change to match JSON ("reservation_code")
)


data class Notification(
    val id: Int,
    val user: CustomUser,
    val message: String,
    val notificationDate: String // Date format might need adjustment
)
