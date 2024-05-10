package com.example.reservation_app_frontend.repository.reservation

import com.example.reservation_app_frontend.data.parking.Parking
import com.example.reservation_app_frontend.data.reservation.Reservation
import com.example.reservation_app_frontend.endpoint.reservation.ReservationEndpoint
import retrofit2.Response

class ReservationRepository(private val endpoint: ReservationEndpoint) {

    suspend fun getMyReservations(): Response<List<Reservation>> {
        return endpoint.getAllReservations()
    }
}