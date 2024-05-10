package com.example.reservation_app_frontend.viewModel.reservation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.reservation_app_frontend.data.reservation.Reservation
import com.example.reservation_app_frontend.repository.parking.ParkingRepository
import com.example.reservation_app_frontend.repository.reservation.ReservationRepository
import com.example.reservation_app_frontend.viewModel.parking.getParkingsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class getMyReservationsViewModel(private val reservationRepository: ReservationRepository) : ViewModel() {
    val reservations = mutableStateListOf<Reservation>()
    var loading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)

    fun fetchReservations() {
        loading.value = true // Set loading to true before fetching data
        error.value = null // Clear any previous error messages

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = reservationRepository.getMyReservations()
                Log.d(TAG, "Response code: ${response.code()}")

                if (response.isSuccessful) {
                    val data = response.body()
                    Log.d(TAG, "Data received: $data")

                    reservations.clear()
                    data?.let {
                        reservations.addAll(it)

                    }
                } else {
                    error.value = "Failed to fetch reservations: ${response.message()}"
                    Log.e(TAG, "Failed to fetch reservations: ${response.message()}")
                }
            } catch (e: Exception) {
                error.value = "Failed to fetch reservations: ${e.message}"
                Log.e(TAG, "Failed to fetch reservations: ${e.message}")
            } finally {
                loading.value = false
            }
        }
    }

    class Factory(private val reservationRepository: ReservationRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return getMyReservationsViewModel(reservationRepository) as T
        }
    }
}