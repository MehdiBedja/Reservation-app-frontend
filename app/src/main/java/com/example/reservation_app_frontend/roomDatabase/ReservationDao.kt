package com.example.reservation_app_frontend.roomDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ReservationDao {

    @Insert
    fun insertReservation(reservationEntity: ReservationEntity)


    @Query("SELECT * FROM ReservationEntity WHERE user = :userId")
    fun getReservationsByUserId(userId: Int): List<ReservationEntity>

    @Query("SELECT * FROM ReservationEntity WHERE id = :reservationId")
    fun getReservationOffline(reservationId: Int?): ReservationEntity


    @Update
     fun updatePaymentStatus(reservation: ReservationEntity)

    @Query("UPDATE ReservationEntity SET payment_status = :newStatus WHERE id = :reservationId")
    suspend fun updatePaymentStatus1(reservationId: Int, newStatus: String)


    @Query("SELECT payment_status FROM ReservationEntity WHERE id = :reservationId")
      fun getPaymentStatus(reservationId: Int): String?

    // Define ReservationWithParking data class to hold Reservation and ParkingEntity objects
}