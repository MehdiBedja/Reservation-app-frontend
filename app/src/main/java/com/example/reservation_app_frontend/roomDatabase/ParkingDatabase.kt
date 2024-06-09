package com.example.reservation_app_frontend.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ReservationEntity::class,
    ],

    version = 2, exportSchema = false
)
abstract class ParkingDatabase : RoomDatabase() {
    abstract val reservationDao: ReservationDao
}