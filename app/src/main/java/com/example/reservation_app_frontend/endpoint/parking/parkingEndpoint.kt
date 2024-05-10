package com.example.reservation_app_frontend.endpoint.parking

import com.example.reservation_app_frontend.data.parking.Parking
import com.example.reservation_app_frontend.network.url
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface parkingEndpoint {
    @GET("parkings/getParkings/")
    suspend fun getAllParkings(): Response<List<Parking>>
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