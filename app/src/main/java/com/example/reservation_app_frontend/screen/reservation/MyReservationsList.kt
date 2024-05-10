package com.example.reservation_app_frontend.screen.reservation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reservation_app_frontend.data.reservation.Reservation
import com.example.reservation_app_frontend.viewModel.reservation.getMyReservationsViewModel

@Composable
fun ShowReservationList(reservationViewModel: getMyReservationsViewModel) {
    AddProgress(reservationViewModel)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.LightGray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.Gray)
        ) {
            Text(
                text = "My Reservations",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(reservationViewModel.reservations) { reservation ->
                ReservationItem(reservation = reservation)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ReservationItem(reservation: Reservation) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(topStart = 20.dp, bottomEnd = 20.dp))
            .background(getShadedColorForReservation(reservation))
            .clickable { /* Handle click event if needed */ }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Reservation ID: ${reservation.id}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "User: ${reservation.user.username}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Parking Place: ${reservation.parking_place?.id ?: "N/A"}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Reservation Date: ${reservation.reservation_date}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Payment Status: ${reservation.payment_status}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Reservation Code: ${reservation.reservation_code}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Composable
fun getShadedColorForReservation(reservation: Reservation): Color {
    val baseColor = when (reservation.payment_status?.lowercase()) {
        "paid" -> Color(0xFFADE772)
        "pending" -> Color(0xFF457BCC)
        "cancelled" -> Color(0xFFDA1061)
        else -> Color.Gray
    }
    val lighterColor = baseColor.copy(alpha = 0.9f)
    val darkerColor = baseColor.copy(alpha = 0.7f)
    return if (reservation.payment_status?.lowercase() == "pending") {
        lighterColor
    } else {
        darkerColor
    }
}

@Composable
fun AddProgress(reservationViewModel: getMyReservationsViewModel) {
    val isLoading = reservationViewModel.loading.value

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}