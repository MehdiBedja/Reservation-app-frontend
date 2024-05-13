package com.example.reservation_app_frontend.endpoint.parking

import com.example.reservation_app_frontend.data.parking.Parking
import com.example.reservation_app_frontend.data.parking.ParkingPlace
import com.example.reservation_app_frontend.network.url
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface parkingEndpoint {
    @GET("parkings/getParkings/")
    suspend fun getAllParkings(): Response<List<Parking>>

    @GET("parkings/getParkingsId/")
    suspend fun getAllParkingsA(): Response<List<Parking>>

    @GET("parkings/getParking/{id}/")
    suspend fun getParking(@Path("id") id: Int?): Response<Parking>


    @GET("reservations/getAllParkingPlaces/{parkingId}/")
    suspend fun getAllParkingPlaces(@Path("parkingId") parkingId: Int): Response<List<ParkingPlace>>

    companion object {
        var endpoint: parkingEndpoint? = null
        fun createEndpoint(): parkingEndpoint {
            if(endpoint ==null) {
                endpoint = Retrofit.Builder().baseUrl(url).
                addConverterFactory(GsonConverterFactory.create()).build().
                create(parkingEndpoint::class.java)
            }
            return endpoint!!

        }

    }


}