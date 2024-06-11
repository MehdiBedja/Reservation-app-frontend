package com.example.reservation_app_frontend.network

import android.content.Context

const val url = "https://a9d8-154-121-31-63.ngrok-free.app/"


object Globals {
    var savedUsername: Int? = null
}

fun initializeUsername(context: Context) {
    val sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)
    Globals.savedUsername = sharedPreferences.getInt("userId", -1)
}



