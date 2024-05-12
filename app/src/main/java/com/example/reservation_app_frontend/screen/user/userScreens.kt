package com.example.reservation_app_frontend.screen.user

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.reservation_app_frontend.endpoint.user.userEndpoint
import com.example.reservation_app_frontend.navigation.Destination
import com.example.reservation_app_frontend.repository.user.AuthRepository
import com.example.reservation_app_frontend.viewModel.user.AccountViewModel
import com.example.reservation_app_frontend.viewModel.user.LoginViewModel


@Composable
fun SignUpScreenOld(viewModel: AccountViewModel) {
    val email = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        // Input fields for user registration data
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
        TextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
        TextField(
            value = firstName.value,
            onValueChange = { firstName.value = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
        TextField(
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        // Sign-up button
        Button(
            onClick = {
                viewModel.signUpUser(
                    email = email.value,
                    username = username.value,
                    lastName = lastName.value,
                    firstName = firstName.value,
                    phoneNumber = phoneNumber.value,
                    password = password.value
                )
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text(text = "Sign Up")
        }

        // Show loading indicator and error message if applicable
        if (viewModel.loading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        viewModel.error.value?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun SignUpScreen(navController: NavHostController) {

    val endpoint = userEndpoint.createEndpoint()
    val authRepository = AuthRepository(endpoint)
//    val viewModel = AccountViewModel.Factory(authRepository).create(AccountViewModel::class.java)
    val viewModel = AccountViewModel.getInstance(authRepository)

    val email = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val createdSuccess by viewModel.createdSuccess

    Column(modifier = Modifier.padding(16.dp)) {
        // Input fields for user registration data
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
        TextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
        TextField(
            value = firstName.value,
            onValueChange = { firstName.value = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
        TextField(
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        // Sign-up button with navigation action
        Button(
            onClick = {
                viewModel.signUpUser(
                    email = email.value,
                    username = username.value,
                    lastName = lastName.value,
                    firstName = firstName.value,
                    phoneNumber = phoneNumber.value,
                    password = password.value
                )

            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text(text = "Sign Up")
        }

        // Show loading indicator and error message if applicable
        if (viewModel.loading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        viewModel.error.value?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        if (viewModel.createdSuccess.value) {
            Log.d(ContentValues.TAG, "time to go to login page!!!!!!")
            LaunchedEffect(Unit) {
                navController.navigate(Destination.LogIn.route)
            }
        }
    }
}


//
//@Composable
//fun LogInScreen(viewModel: LoginViewModel) {
//    val email = remember { mutableStateOf("") }
//    val password = remember { mutableStateOf("") }
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        // Input fields for email and password
//        TextField(
//            value = email.value,
//            onValueChange = { email.value = it },
//            label = { Text("Email") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        TextField(
//            value = password.value,
//            onValueChange = { password.value = it },
//            label = { Text("Password") },
//            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
//        )
//
//        // Login button
//        Button(
//            onClick = {
//                viewModel.loginUser(email.value, password.value)
//            },
//            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
//        ) {
//            Text(text = "Login")
//        }
//
//        // Show loading indicator and error message if applicable
//        if (viewModel.loading.value) {
//            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
//        }
//        viewModel.error.value?.let { errorMessage ->
//            Text(
//                text = errorMessage,
//                color = Color.Red,
//                modifier = Modifier.padding(vertical = 8.dp)
//            )
//        }
//    }
//}


@Composable
fun LogInScreen( navController: NavHostController) {


    val endpoint = userEndpoint.createEndpoint()
    val authRepository = AuthRepository(endpoint)
//    val viewModel = LoginViewModel.Factory(authRepository).create(LoginViewModel::class.java)
    val viewModel = LoginViewModel.getInstance(authRepository)

    val logged by viewModel.token


    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        // Input fields for email and password
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        // Login button with navigation action
        Button(
            onClick = {
                viewModel.loginUser(email.value, password.value)
//
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text(text = "Login")
        }

        // Show loading indicator and error message if applicable
        if (viewModel.loading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        viewModel.error.value?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        if (viewModel.token.value != null) {
            LaunchedEffect(Unit) {
                navController.navigate(Destination.ListOfParkings.route)
            }
        }


    }
}
