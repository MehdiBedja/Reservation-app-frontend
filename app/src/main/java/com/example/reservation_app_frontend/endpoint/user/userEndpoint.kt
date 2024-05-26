package com.example.reservation_app_frontend.endpoint.user

import com.example.reservation_app_frontend.data.parking.Parking
import com.example.reservation_app_frontend.data.user.LoginRequest
import com.example.reservation_app_frontend.data.user.LoginResponse
import com.example.reservation_app_frontend.data.user.SignUpRequest
import com.example.reservation_app_frontend.data.user.User
import com.example.reservation_app_frontend.network.url
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface userEndpoint {

    //auth


    @POST("user/signup")
    suspend fun signUp(@Body request: SignUpRequest): Response<ResponseBody>

    @POST("user/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    //get user info
    @GET("user/getUser/{id}/")
    suspend fun getUser(@Path("id") id: Int?): Response<User>


    companion object {
        var endpoint: userEndpoint? = null
        fun createEndpoint(): userEndpoint {
            if(endpoint ==null) {
                endpoint = Retrofit.Builder().baseUrl(url).
                addConverterFactory(GsonConverterFactory.create()).build().
                create(userEndpoint::class.java)
            }
            return endpoint!!

        }

    }


}