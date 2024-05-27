package com.example.reservation_app_frontend

import AppDatabase
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.reservation_app_frontend.endpoint.parking.parkingEndpoint
import com.example.reservation_app_frontend.endpoint.reservation.ReservationEndpoint
import com.example.reservation_app_frontend.endpoint.user.userEndpoint
import com.example.reservation_app_frontend.network.initializeUsername
import com.example.reservation_app_frontend.preferences.Preferences

import com.example.reservation_app_frontend.repository.parking.ParkingRepository
import com.example.reservation_app_frontend.repository.reservation.ReservationRepository
import com.example.reservation_app_frontend.repository.user.AuthRepository
import com.example.reservation_app_frontend.repository.user.UserPreferences
import com.example.reservation_app_frontend.screen.navigation.MainScreen
import com.example.reservation_app_frontend.ui.theme.Reservation_app_frontendTheme
import com.example.reservation_app_frontend.viewModel.parking.getParkingsViewModel
import com.example.reservation_app_frontend.viewModel.reservation.AddReservationViewModel
import com.example.reservation_app_frontend.viewModel.reservation.getMyReservationsViewModel
import com.example.reservation_app_frontend.viewModel.user.LoginViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import getAllReservationModel





class MainActivity : ComponentActivity() {


    @SuppressLint("SuspiciousIndentation")
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the application context and pass it to getInstance
        //   val appContext = applicationContext
        //    Log.d("MainActivity", "AppContext: $appContext")
        //    val database = AppDatabase.getInstance(appContext)
        setContent {
            Reservation_app_frontendTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Create an instance of your ViewModel and fetch parkings outside the setContent block

                    initializeUsername(this)

                    val endpoint5 = userEndpoint.createEndpoint()
                    val userRepo by lazy {AuthRepository(endpoint5)}

                    val preferences by lazy { Preferences(this) }
                    val userPreferences by lazy { UserPreferences(preferences) }

                    val userViewModel = LoginViewModel.getInstance(userRepo , userPreferences)



                    var startDestination = "Parkings List"

                    if ( !userViewModel.isConnected()) {
                        startDestination = "LogIn"
                    }



                val navController = rememberNavController()
                    MainScreen( navController , this , startDestination ,  userViewModel ,
                        // appDatabase

               )
                   // MapScreen()

                }



            }
        }
    }
}

