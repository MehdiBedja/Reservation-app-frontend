package com.example.reservation_app_frontend.viewModel.reservation

import ReservationEntity
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.reservation_app_frontend.data.reservation.ReservationDTO
import com.example.reservation_app_frontend.data.reservation.Reservation
import com.example.reservation_app_frontend.repository.reservation.ReservationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AddReservationViewModel(private val reservationRepository: ReservationRepository) : ViewModel() {


    val loading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)
    val reservationAdded = mutableStateOf(false)

    fun addReservation(reservationDTO: ReservationDTO) {
        loading.value = true
        error.value = null
        reservationAdded.value = false
        Log.d(TAG, "ReservationDTO to be sent: $reservationDTO") // Logging the ReservationDTO


        CoroutineScope(Dispatchers.IO).launch {
            try {
                reservationRepository.createReservation(reservationDTO)
                reservationAdded.value = true
            } catch (e: Exception) {
                error.value = "Failed to add reservation: ${e.message}"
                Log.e(TAG, "Failed to add reservation: ${e.message}")
            } finally {
                loading.value = false
            }
        }

    }


    // Offline reservation creation
    //    fun addReservationOffline(reservationDTO: ReservationDTO) {
    //       CoroutineScope(Dispatchers.IO).launch {
    //        try {
    //            val reservationEntity = ReservationEntity(reservationDTO)
    //            reservationRepository.createReservationOffline(reservationEntity)
    //            reservationAdded.value = true
    //        } catch (e: Exception) {
    //            error.value = "Failed to add reservation offline: ${e.message}"
    //             Log.e(TAG, "Failed to add reservation offline: ${e.message}")
    //         } finally {
    //             loading.value = false
    //         }
    //       }
    //  }

    // Offline reservation retrieval
    //   fun getAllReservationsOffline(): LiveData<List<ReservationEntity>> {
    //       return reservationRepository.getAllReservationsOffline()
    //   }
    //  companion object {
    //      private const val TAG = "AddReservationViewModel"
    //   }

    class Factory(private val reservationRepository: ReservationRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddReservationViewModel(reservationRepository) as T
        }
    }
}