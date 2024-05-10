package com.example.reservation_app_frontend.endpoint.reservation

import com.example.reservation_app_frontend.data.parking.Parking
import com.example.reservation_app_frontend.data.reservation.Reservation
import com.example.reservation_app_frontend.network.url
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface ReservationEndpoint {
    @GET("reservations/myReservations/1/") // Update the endpoint URL as needed
    suspend fun getAllReservations(): Response<List<Reservation>>

    companion object {
        var endpoint: ReservationEndpoint? = null
        fun createEndpoint(): ReservationEndpoint {
            if (endpoint == null) {
                endpoint = Retrofit.Builder()
                    .baseUrl(url) // Make sure 'url' is defined or replace it with your base URL
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ReservationEndpoint::class.java)
            }
            return endpoint!!
        }
    }
}