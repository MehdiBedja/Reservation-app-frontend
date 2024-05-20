package com.example.reservation_app_frontend.repository.reservation

import com.example.reservation_app_frontend.data.parking.Parking
import com.example.reservation_app_frontend.data.reservation.Reservation
import com.example.reservation_app_frontend.data.reservation.ReservationDTO
import com.example.reservation_app_frontend.data.reservation.ReservationDTO2
import com.example.reservation_app_frontend.endpoint.reservation.ReservationEndpoint
import retrofit2.Response

class ReservationRepository(private val endpoint: ReservationEndpoint) {

    suspend fun getMyReservations(username: String): Response<List<Reservation>> {
        return endpoint.getAllReservations(username)
    }
    suspend fun createReservation(reservationDTO: ReservationDTO) {
        endpoint.createReservation(reservationDTO)
    }

    suspend fun getReservation(id: Int?): Response<ReservationDTO2> {
        return endpoint.getReservation(id)
    }
}