package com.example.reservation_app_frontend.viewModel.parking

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.reservation_app_frontend.data.parking.Parking
import com.example.reservation_app_frontend.repository.parking.ParkingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class getParkingsViewModel(private val parkingRepository: ParkingRepository): ViewModel() {
    val parkings = mutableStateListOf<Parking>()
    var loading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)

    val parking = mutableStateOf<Parking?>(null)
    var loading1 = mutableStateOf(false)
    val error1 = mutableStateOf<String?>(null)

    fun fetchParkings(): List<Parking>? {
        loading.value = true // Set loading to true before fetching data
        error.value = null // Clear any previous error messages

        var parkingsData: List<Parking>? = null

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = parkingRepository.getParkings()
                Log.d(TAG, "Response code: ${response.code()}")

                if (response.isSuccessful) {
                    val data = response.body()
                    Log.d(TAG, "Data received: $data")

                    parkings.clear()
                    data?.let {
                        parkings.addAll(it)
                        Log.d(TAG, "Parkings added to list: $parkings")
                    }
                    parkingsData = data
                } else {
                    error.value = "Failed to fetch parkings: ${response.message()}"
                    Log.e(TAG, "Failed to fetch parkings: ${response.message()}")
                }
            } catch (e: Exception) {
                error.value = "Failed to fetch parkings: ${e.message}"
                Log.e(TAG, "Failed to fetch parkings: ${e.message}")
            } finally {
                loading.value = false
            }
        }

        return parkingsData
    }


    fun getParkingById(parkingId: Int?) {
        loading1.value = true // Set loading to true before fetching data
        error1.value = null // Clear any previous error messages

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = parkingRepository.getParkingById(parkingId)
                Log.d(TAG, "Response code: ${response.code()}")

                if (response.isSuccessful) {

                    val data = response.body()
                    parking.value = data

                    Log.d(TAG, "Data received: $data")
                } else {
                    error1.value = "Failed to fetch parking: ${response.message()}"
                    Log.e(TAG, "Failed to fetch parking: ${response.message()}")
                }
            } catch (e: Exception) {
                error1.value = "Failed to fetch parking: ${e.message}"
                Log.e(TAG, "Failed to fetch parking: ${e.message}")
            } finally {
                loading1.value = false
            }
        }
    }

    class Factory(private val parkingRepository: ParkingRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return getParkingsViewModel(parkingRepository) as T
        }
    }
}
