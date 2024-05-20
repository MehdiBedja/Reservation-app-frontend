package com.example.reservation_app_frontend.network

import android.content.Context

const val url = "https://4b2e-41-111-189-195.ngrok-free.app/"


object Globals {
    var savedUsername: String? = null
}

fun initializeUsername(context: Context) {
    val sharedPreferences = context.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
    Globals.savedUsername = sharedPreferences.getString("username", null)
}



