package com.example.reservation_app_frontend.preferences

import android.content.Context
import androidx.core.content.edit
import com.example.reservation_app_frontend.network.Globals

class Preferences (private val context: Context) {


    fun isConnected():Boolean {
        val pref = context.getSharedPreferences("user", Context.MODE_PRIVATE)
        val connected = pref.getBoolean("connected",false)
        return  connected
    }

    fun updateValues(connected:Boolean,userId:Int) {
        val pref = context.getSharedPreferences("user", Context.MODE_PRIVATE)
        Globals.savedUsername = userId
        pref.edit {
            putInt("userId",userId)
            putBoolean("connected",connected)

        }
    }

    fun clearCredentials() {
        val pref = context.getSharedPreferences("user", Context.MODE_PRIVATE)
        pref.edit {
            remove("userId")
            remove("connected")
        }
    }


}