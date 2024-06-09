package com.example.reservation_app_frontend.roomDatabase

import android.content.Context
import androidx.room.Room


object DatabaseManager {
    private lateinit var database: ParkingDatabase

    fun initialize(context: Context) {
        database = Room.databaseBuilder(
            context.applicationContext,
            ParkingDatabase::class.java,
            "parkingDatabase"
        ).build()
    }


    val reservationDao: ReservationDao
        get() = database.reservationDao
}