package com.example.reservation_app_frontend.data.parking

data class Parking(
    val name : String,
    val description : String,
    val commune_name : String,
    val image :String
)
data class Wilaya(
    val id: Int,
    val name: String
)

data class Daira(
    val id: Int,
    val name: String,
    val wilaya: Wilaya
)

data class Commune(
    val id: Int,
    val name: String,
    val daira: Daira
)

data class ParkingPlace(
    val id: Int,
    val attributes: String,
    val parking: Parking
)