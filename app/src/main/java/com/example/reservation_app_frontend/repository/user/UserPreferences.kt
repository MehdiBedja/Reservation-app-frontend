package com.example.reservation_app_frontend.repository.user

import com.example.reservation_app_frontend.preferences.Preferences

class UserPreferences(private val preferences: Preferences) {

    fun isConnected() = preferences.isConnected()

    fun updateValues(connected:Boolean,userId:Int) = preferences.updateValues(connected,userId)
}