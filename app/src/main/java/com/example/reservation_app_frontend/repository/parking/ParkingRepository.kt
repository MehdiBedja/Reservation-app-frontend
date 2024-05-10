package com.example.reservation_app_frontend.repository.parking

import com.example.reservation_app_frontend.data.parking.Parking
import com.example.reservation_app_frontend.endpoint.parking.parkingEndpoint
import retrofit2.Response

class ParkingRepository(private val endpoint: parkingEndpoint) {

    suspend fun getParkings(): Response<List<Parking>> {
        return endpoint.getAllParkings()
    }
}