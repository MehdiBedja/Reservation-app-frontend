package com.example.reservation_app_frontend.notification


import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import kotlin.math.abs

fun scheduleReservationNotification(context: Context, date: LocalDate, time: LocalTime) {
    val reservationDateTime = LocalDateTime.of(date, time)
    val currentDateTime = LocalDateTime.now()
    val timeDifferenceMinutes = Duration.between(currentDateTime, reservationDateTime).toMinutes()
    Log.d("scheduleReservationNotification", "Scheduling notification for $reservationDateTime in $timeDifferenceMinutes minutes")

    if (timeDifferenceMinutes in 10..20) { // Change the range to 10..20
        val notificationWork: WorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(timeDifferenceMinutes - 10, TimeUnit.MINUTES) // Adjust the delay to be 10 minutes before the reservation
            .build()
        WorkManager.getInstance(context).enqueue(notificationWork)
        Log.d("scheduleReservationNotification", "NotificationWorker enqueued with delay of ${timeDifferenceMinutes - 10} minutes")
    } else {
        Log.d("scheduleReservationNotification", "Notification not scheduled, time difference not within range")
    }
}