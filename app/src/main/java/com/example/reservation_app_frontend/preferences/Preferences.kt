package com.example.reservation_app_frontend.preferences

import android.content.Context
import androidx.core.content.edit
import com.example.reservation_app_frontend.network.Globals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Preferences (private val context: Context) {

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected

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