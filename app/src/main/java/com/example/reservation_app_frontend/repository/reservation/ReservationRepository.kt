package com.example.reservation_app_frontend.repository.reservation

import com.example.reservation_app_frontend.data.reservation.Reservation
import com.example.reservation_app_frontend.data.reservation.ReservationDTO
import com.example.reservation_app_frontend.data.reservation.ReservationDTO2
import com.example.reservation_app_frontend.endpoint.reservation.ReservationEndpoint
import retrofit2.Response

class ReservationRepository(private val endpoint: ReservationEndpoint
  //, private val appDatabase: AppDatabase
) {

    //val reservationDao = appDatabase.getReservationDao()

    suspend fun getMyReservations(id: Int): Response<List<Reservation>> {
        return endpoint.getAllReservations(id)
    }

    suspend fun createReservation(reservationDTO: ReservationDTO) {
        endpoint.createReservation(reservationDTO)
    }

    suspend fun getReservation(id: Int?): Response<ReservationDTO2> {
        return endpoint.getReservation(id)
    }

 //   suspend fun createReservationOffline(reservationEntity: com.example.reservation_app_frontend.roomDatabase.ReservationEntity) {
    //       withContext(Dispatchers.IO) {
    //           reservationDao.insertReservation(reservationEntity)
    //       }
    //   }

    // Offline operation
    //   fun getAllReservationsOffline(): LiveData<List<com.example.reservation_app_frontend.roomDatabase.ReservationEntity>> {
    // //       return reservationDao.getAllReservations()
    //   }
}