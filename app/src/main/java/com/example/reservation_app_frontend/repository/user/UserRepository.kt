package com.example.reservation_app_frontend.repository.user

import com.example.reservation_app_frontend.data.user.LoginRequest
import com.example.reservation_app_frontend.data.user.LoginResponse
import com.example.reservation_app_frontend.data.user.SignUpRequest
import com.example.reservation_app_frontend.data.user.User
import com.example.reservation_app_frontend.data.user.User_signUp
import com.example.reservation_app_frontend.endpoint.user.userEndpoint
import okhttp3.ResponseBody
import retrofit2.Response


class AuthRepository(private val endpoint: userEndpoint) {

    suspend fun signUpUser(
        email: String,
        username: String,
        lastName: String,
        firstName: String,
        phoneNumber: String,
        password: String
    ): Response<ResponseBody> {
        val signUpRequest = SignUpRequest(
            userType = "client",
            user = User_signUp(
                email = email,
                username = username,
                lastName = lastName,
                firstName = firstName,
                phoneNumber = phoneNumber,
                password = password
            )
        )
        return endpoint.signUp(signUpRequest)
    }

    suspend fun loginUser(username: String, password: String): Response<LoginResponse> {
        val loginRequest = LoginRequest(
            username = username,
            password = password
        )
        return endpoint.login(loginRequest)
    }

    suspend fun getUser(userId : Int) : Response<User> {
        return endpoint.getUser(userId)
    }


}
