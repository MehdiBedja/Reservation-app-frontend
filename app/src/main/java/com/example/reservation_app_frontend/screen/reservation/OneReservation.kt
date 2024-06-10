package com.example.reservation_app_frontend.screen.reservation


import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.reservation_app_frontend.endpoint.reservation.ReservationEndpoint
import com.example.reservation_app_frontend.repository.reservation.ReservationRepository
import com.example.reservation_app_frontend.viewModel.reservation.getMyReservationsViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun OneReservation(reservationId: Int?
    //     , appDatabase: AppDatabase
) {


    val endpoint = ReservationEndpoint.createEndpoint()
    val reservationRepository by lazy { ReservationRepository(endpoint
        //    , appDatabase
    ) }

    val reservationViewModel = getMyReservationsViewModel.Factory(reservationRepository).create(getMyReservationsViewModel::class.java)

    val reservation by remember { reservationViewModel.reservationoff }
    val isLoading by remember { reservationViewModel.loading }

    LaunchedEffect(Unit) {
        reservationViewModel.getReservationOffline(reservationId) // Start loading
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
                    Text(
                        text = "Parking place : ${it.parking_place}",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = "Entry DateTime: ${it.entry_datetime}",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = "Exit DateTime: ${it.exit_datetime}",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = "Reservation Code: ${it.reservation_code}",
                        style = MaterialTheme.typography.labelMedium
                    )
                    // You can add more details or customize the layout as needed

                    // Payment Status Section
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* Handle click action */ },
                        contentAlignment = Alignment.Center
                    ) {
                        val paymentStatusColor = when (it.payment_status) {
                            "validated" -> MaterialTheme.colorScheme.primary // Green color for validated
                            "pending" -> MaterialTheme.colorScheme.secondary // Orange color for pending
                            else -> MaterialTheme.colorScheme.error // Red color for other cases
                        }
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .background(paymentStatusColor)
                                .clickable { /* Handle click action */ }
                        ) {
                            Text(
                                text = "Payment Status: ${it.payment_status}",
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(8.dp),
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = {
/*                                    .launch  {
                                        reservationViewModel.updatePaymentStatus(reservationId, "Validated")
                                    }*/
                                },
                                modifier = Modifier.padding(start = 8.dp),
                            ) {
                                Text(text = "Validate Payment", style = MaterialTheme.typography.labelMedium)
                            }
                        }

                    }
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