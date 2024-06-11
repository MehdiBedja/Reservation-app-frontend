package com.example.reservation_app_frontend

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.reservation_app_frontend.endpoint.user.userEndpoint
import com.example.reservation_app_frontend.network.Globals
import com.example.reservation_app_frontend.network.initializeUsername
import com.example.reservation_app_frontend.notification.FetchReservationsWorker
import com.example.reservation_app_frontend.notification.scheduleReservationNotification
import com.example.reservation_app_frontend.preferences.Preferences
import com.example.reservation_app_frontend.repository.user.AuthRepository
import com.example.reservation_app_frontend.repository.user.UserPreferences
import com.example.reservation_app_frontend.roomDatabase.DatabaseManager
import com.example.reservation_app_frontend.screen.navigation.MainScreen
import com.example.reservation_app_frontend.ui.theme.Reservation_app_frontendTheme
import com.example.reservation_app_frontend.viewModel.user.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity() {
    @SuppressLint("SuspiciousIndentation")
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContext = applicationContext
        Log.d("MainActivity", "AppContext: $appContext")

        DatabaseManager.initialize(appContext)

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.d("MainActivity", "Notification permission granted")
                schedulePeriodicFetchReservationsWorker()
            } else {
                Log.d("MainActivity", "Notification permission not granted")
            }
        }




        setContent {
            Reservation_app_frontendTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    initializeUsername(this)

                    val endpoint5 = userEndpoint.createEndpoint()
                    val userRepo by lazy { AuthRepository(endpoint5) }

                    val preferences by lazy { Preferences(this) }
                    val userPreferences by lazy { UserPreferences(preferences) }

                    val userViewModel = LoginViewModel.getInstance(userRepo, userPreferences)

                    var startDestination = "Parkings List"

                    if (!userViewModel.isConnected()) {
                        startDestination = "LogIn"
                    }

                    val navController = rememberNavController()
                    MainScreen(navController, this, startDestination, userViewModel)
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        schedulePeriodicFetchReservationsWorker()
    }



    private fun schedulePeriodicFetchReservationsWorker() {
        Log.d("MainActivity", "Scheduling periodic FetchReservationsWorker")
        val fetchReservationsWorkRequest = PeriodicWorkRequestBuilder<FetchReservationsWorker>(
          15   , TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "FetchReservationsWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            fetchReservationsWorkRequest
        )

        Log.d("MainActivity", "Periodic FetchReservationsWorker scheduled")
    }
}