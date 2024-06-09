package com.example.reservation_app_frontend.roomDatabase// RoomEntities.kt

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReservationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val user: Int?,
  //  val parking_place: Int,
  //  val entry_datetime: String?,
   // val exit_datetime: String?,
   // val payment_status: String?,
   // val reservation_code: String?,
   // var isSynced: Boolean = false // Added field for offline insertion tracking
)

