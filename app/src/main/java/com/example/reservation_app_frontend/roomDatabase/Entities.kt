package com.example.reservation_app_frontend.roomDatabase// RoomEntities.kt

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Entity
data class ReservationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val user: Int?,
    val parking_place: String,
    val entry_datetime: String?,
    val exit_datetime: String?,
    var payment_status: String?,
    val reservation_code: String?,
    val date: LocalDate?,
    val time: LocalTime?

)


class Converters {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let {
            LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE)
        }
    }

    @TypeConverter
    fun fromLocalTime(time: LocalTime?): String? {
        return time?.format(DateTimeFormatter.ISO_LOCAL_TIME)
    }

    @TypeConverter
    fun toLocalTime(timeString: String?): LocalTime? {
        return timeString?.let {
            LocalTime.parse(it, DateTimeFormatter.ISO_LOCAL_TIME)
        }
    }
}
