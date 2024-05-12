package com.example.reservation_app_frontend.viewModel.user

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.reservation_app_frontend.repository.user.AuthRepository
import kotlinx.coroutines.launch


//

class AccountViewModel(private val authRepository: AuthRepository) : ViewModel() {
    var loading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)

    fun signUpUser(
        email: String,
        username: String,
        lastName: String,
        firstName: String,
        phoneNumber: String,
        password: String
    ) {
        loading.value = true // Set loading to true before sending sign-up request
        error.value = null // Clear any previous error messages

        viewModelScope.launch {
            try {
                val response = authRepository.signUpUser(
                    email = email,
                    username = username,
                    lastName = lastName,
                    firstName = firstName,
                    phoneNumber = phoneNumber,
                    password = password
                )
                if (response.isSuccessful) {
                    // Handle successful sign-up
                    Log.d(ContentValues.TAG, "Sign-up successful")
                } else {
                    error.value = "Failed to sign up: ${response.message()}"
                    Log.e(ContentValues.TAG, "Failed to sign up: ${response.message()}")
                }
            } catch (e: Exception) {
                error.value = "Failed to sign up: ${e.message}"
                Log.e(ContentValues.TAG, "Failed to sign up: ${e.message}")
            } finally {
                loading.value = false
            }
        }
    }

    class Factory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AccountViewModel(authRepository) as T
        }
    }
}


class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    var loading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)

    fun loginUser(email: String, password: String) {
        loading.value = true
        error.value = null

        viewModelScope.launch {
            try {
                val response = authRepository.loginUser(email, password)
                if (response.isSuccessful) {
                    // Handle successful login
                    Log.d(ContentValues.TAG, "Login successful")
                } else {
                    error.value = "Failed to login: ${response.message()}"
                    Log.e(ContentValues.TAG, "Failed to login: ${response.message()}")
                }
            } catch (e: Exception) {
                error.value = "Failed to login: ${e.message}"
                Log.e(ContentValues.TAG, "Failed to login: ${e.message}")
            } finally {
                loading.value = false
            }
        }
    }

    class Factory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(authRepository) as T
        }
    }
}
