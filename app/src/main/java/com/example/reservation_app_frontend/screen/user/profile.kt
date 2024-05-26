package com.example.reservation_app_frontend.screen.user

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reservation_app_frontend.data.user.User
import com.example.reservation_app_frontend.network.Globals
import com.example.reservation_app_frontend.viewModel.user.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserProfileScreen(viewModel: LoginViewModel, navController: NavController) {
    val userState by remember { viewModel.user }
    val loading by remember { viewModel.loading }
    val error by remember { viewModel.error }

    val userId = Globals.savedUsername
    // Fetch user data when the screen is first displayed
    LaunchedEffect(userId) {
        if (userId != null) {
            viewModel.getUser(userId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (loading) {
                    CircularProgressIndicator()
                } else if (error != null) {
                    Text("Error: $error")
                } else {
                    userState?.let { user ->
                        UserInfo(user = user)
                    }
                }
            }
        }
    )
}

@Composable
fun UserInfo(user: User) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                UserDetailItem(label = "Username:", value = user.username)
                UserDetailItem(label = "Email:", value = user.email)
                UserDetailItem(label = "Last Name:", value = user.last_name ?: "N/A")
                UserDetailItem(label = "First Name:", value = user.first_name ?: "N/A")
                UserDetailItem(label = "Phone Number:", value = user.phone_number ?: "N/A")
                // Add more fields as needed
            }

        }
    }
}

@Composable
fun UserDetailItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(color = Color(0xFFE6E6E6), shape = RoundedCornerShape(4.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF333333), // Dark gray color
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.background(color = Color.LightGray, shape = RoundedCornerShape(4.dp)).padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}