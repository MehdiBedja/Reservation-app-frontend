package com.example.reservation_app_frontend.screen.reservation


import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reservation_app_frontend.endpoint.reservation.ReservationEndpoint
import com.example.reservation_app_frontend.repository.reservation.ReservationRepository
import com.example.reservation_app_frontend.viewModel.reservation.getMyReservationsViewModel

@Composable
fun OneReservation(reservationId: Int?
    //     , appDatabase: AppDatabase
) {


    val endpoint = ReservationEndpoint.createEndpoint()
    val reservationRepository by lazy { ReservationRepository(endpoint
        //    , appDatabase
    ) }

    val reservationViewModel = getMyReservationsViewModel.Factory(reservationRepository).create(getMyReservationsViewModel::class.java)

    val reservation by remember { reservationViewModel.reservation }
    val isLoading by remember { reservationViewModel.loading }

    LaunchedEffect(Unit) {
        reservationViewModel.getReservationById(reservationId) // Start loading
    }

    if (isLoading) {
        AddProgress1(reservationViewModel = reservationViewModel, reservationId = reservationId)
    } else {
        reservation?.let {
            Log.d("OneReservation", "Reservation not null: $it")
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    it.parking_place?.let { parkingPlace ->
                        Text(text = "Parking Place ID: ${parkingPlace.attributes}", style = MaterialTheme.typography.labelMedium)
                        // You can add more details about the parking place if needed
                    }
                    Text(text = "Reservation Date: ${it.reservation_date}", style = MaterialTheme.typography.labelMedium)
                    Text(text = "Entry DateTime: ${it.entry_datetime}", style = MaterialTheme.typography.labelMedium)
                    Text(text = "Exit DateTime: ${it.exit_datetime}", style = MaterialTheme.typography.labelMedium)
                    Text(text = "Payment Status: ${it.payment_status}", style = MaterialTheme.typography.labelMedium)
                    Text(text = "Reservation Code: ${it.reservation_code}", style = MaterialTheme.typography.labelMedium)
                    // You can add more details or customize the layout as needed
                }
            }
        } ?: run {
            Log.e("OneReservation", "Reservation is null")
            // Handle null reservation scenario, maybe show an error message or navigate back
        }
    }
}

@Composable
fun AddProgress1(reservationViewModel: getMyReservationsViewModel, reservationId: Int?) {
    LaunchedEffect(Unit) {
        reservationViewModel.getReservationById(reservationId) // Start loading
    }

    val isLoading = reservationViewModel.loading1.value

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}