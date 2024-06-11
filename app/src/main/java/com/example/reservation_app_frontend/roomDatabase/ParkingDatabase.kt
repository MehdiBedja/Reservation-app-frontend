package com.example.reservation_app_frontend.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        ReservationEntity::class,
    ],

    version = 6, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ParkingDatabase : RoomDatabase() {
    abstract val reservationDao: ReservationDao
}