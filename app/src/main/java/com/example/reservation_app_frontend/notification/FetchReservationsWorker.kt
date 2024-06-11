package com.example.reservation_app_frontend.notification

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.reservation_app_frontend.network.Globals
import com.example.reservation_app_frontend.roomDatabase.DatabaseManager
import java.time.LocalDateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FetchReservationsWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        val context = applicationContext

        Log.d("FetchReservationsWorker", "FetchReservationsWorker started")

        // Use a coroutine to fetch reservations and schedule notifications
        CoroutineScope(Dispatchers.IO).launch {
            val userId = Globals.savedUsername ?: 0
            if (userId != 0) {
                val reservations = DatabaseManager.reservationDao.getReservationsByUserId(userId)
                Log.d("FetchReservationsWorker", "Fetched ${reservations.size} reservations for user $userId")
                for (reservation in reservations) {
                    Log.d("FetchReservationsWorker", "Processing reservation ${reservation.id} for date ${reservation.date} and time ${reservation.time}")
                    reservation.date?.let { date ->
                        reservation.time?.let { time ->
                             scheduleReservationNotification(context, date, time)
                        }
                    }
                }
            } else {
                Log.d("FetchReservationsWorker", "User ID is 0, no reservations fetched")
            }
        }

        return Result.success()
    }
}