package com.example.reservation_app_frontend.endpoint.reservation

import com.example.reservation_app_frontend.data.parking.Parking
import com.example.reservation_app_frontend.data.reservation.Reservation
import com.example.reservation_app_frontend.data.reservation.ReservationDTO
import com.example.reservation_app_frontend.data.reservation.ReservationDTO2
import com.example.reservation_app_frontend.network.url
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ReservationEndpoint {
    @GET("reservations/myReservations/1/") // Update the endpoint URL as needed
    suspend fun getAllReservations(): Response<List<Reservation>>


    @POST("reservations/addReservation/") // Include the trailing slash
    suspend fun createReservation(@Body reservationDTO: ReservationDTO)




    @GET("reservations/getReservation/{id}/")
    suspend fun getReservation(@Path("id") id: Int?): Response<ReservationDTO2>



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