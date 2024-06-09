package com.example.reservation_app_frontend

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.reservation_app_frontend.endpoint.user.userEndpoint
import com.example.reservation_app_frontend.network.initializeUsername
import com.example.reservation_app_frontend.preferences.Preferences
import com.example.reservation_app_frontend.repository.user.AuthRepository
import com.example.reservation_app_frontend.repository.user.UserPreferences
import com.example.reservation_app_frontend.roomDatabase.DatabaseManager
import com.example.reservation_app_frontend.roomDatabase.ReservationEntity
import com.example.reservation_app_frontend.screen.navigation.MainScreen
import com.example.reservation_app_frontend.ui.theme.Reservation_app_frontendTheme
import com.example.reservation_app_frontend.viewModel.user.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {


    @SuppressLint("SuspiciousIndentation")
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Get the application context and pass it to getInstance
           val appContext = applicationContext
            Log.d("MainActivity", "AppContext: $appContext")




        DatabaseManager.initialize(appContext)


/*        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                DatabaseManager.reservationDao.insertReservation(
                    ReservationEntity(
                        1,
                        2
                    )
                )

            }
        }*/






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

