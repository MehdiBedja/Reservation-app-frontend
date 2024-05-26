package com.example.reservation_app_frontend.viewModel.user

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.example.reservation_app_frontend.data.parking.Parking
import com.example.reservation_app_frontend.data.user.User
import com.example.reservation_app_frontend.repository.user.AuthRepository
import com.example.reservation_app_frontend.repository.user.UserPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.isSensitiveHeader

//





class AccountViewModel(private val authRepository: AuthRepository ) : ViewModel() {
    var loading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)
    val createdSuccess=mutableStateOf(false)

    val login = mutableStateOf(false)
    val logout = mutableStateOf(false)








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
                    createdSuccess.value= true
                    Log.d(ContentValues.TAG, "time to go to login page")


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

//    class Factory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return AccountViewModel(authRepository) as T
//        }
//    }

    companion object {
        @Volatile
        private var instance: AccountViewModel? = null

        fun getInstance(authRepository: AuthRepository): AccountViewModel {
            return instance ?: synchronized(this) {
                instance ?: AccountViewModel(authRepository).also { instance = it }
            }
        }
    }
}


class LoginViewModel(private val authRepository: AuthRepository  ,  private val userPreferences: UserPreferences) : ViewModel() {

    var loading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)
    val token = mutableStateOf<String?>(null)


    val login = mutableStateOf(false)
    val logout = mutableStateOf(false)

    fun isConnected() = userPreferences.isConnected()

    fun logout() {
        userPreferences.updateValues(false,-1)
        logout.value = true
        login.value = false

    }

    val user = mutableStateOf<User?>(null) // Add a mutable state for the user
    var loading1 = mutableStateOf(false)
    val error1 = mutableStateOf<String?>(null)

    fun getUser(userId: Int) {
        loading1.value = true
        error1.value = null

        viewModelScope.launch {
            try {
                val response = authRepository.getUser(userId)
                if (response.isSuccessful) {
                    user.value = response.body()
                } else {
                    error.value = "Failed to fetch user: ${response.message()}"
                }
            } catch (e: Exception) {
                error.value = "Failed to fetch user: ${e.message}"
            } finally {
                loading.value = false
            }
        }
    }

    fun loginUser(email: String, password: String) {
        loading.value = true
        error.value = null

        viewModelScope.launch {
            try {
                val response = authRepository.loginUser(email, password)
                if (response.isSuccessful) {
                    // Handle successful login
                    val jsonResponse = response.body() // Assuming the token is in the response body
                    val tokenValue = jsonResponse?.token // Adjust this based on your actual JSON structure
                    token.value = tokenValue // Save the token in the token variable
                    Log.d(ContentValues.TAG, "Login successful. Token: ${token.value}")


                    val userIdString = jsonResponse?.user?.id
                    val userId = userIdString // Convert to Int, or null if conversion fails

                    if (userId != null) {
                        userPreferences.updateValues(true,userId)
                    }
                    login.value = true
                    logout.value = false

                } else {
                    error.value = "Failed to login: ${response.message()}"
                    Log.e(ContentValues.TAG, "Failed to login: ${response.message()}")
                    token.value=null
                }
            } catch (e: Exception) {
                error.value = "Failed to login: ${e.message}"
                Log.e(ContentValues.TAG, "Failed to login: ${e.message}")
                token.value=null
            } finally {
                loading.value = false
            }
        }
    }

//    class Factory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return LoginViewModel(authRepository) as T
//        }
//    }


    companion object {
        @Volatile
        private var instance: LoginViewModel? = null

        fun getInstance(authRepository: AuthRepository, userPreferences: UserPreferences): LoginViewModel {
            return instance ?: synchronized(this) {
                instance ?: LoginViewModel(authRepository, userPreferences).also { instance = it }
            }
        }
    }
}
