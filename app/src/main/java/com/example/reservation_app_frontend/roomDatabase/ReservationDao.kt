package com.example.reservation_app_frontend.roomDatabase

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface ReservationDao {

    @Insert
    fun insertReservation(reservationEntity: ReservationEntity)

    // Define ReservationWithParking data class to hold Reservation and ParkingEntity objects
}