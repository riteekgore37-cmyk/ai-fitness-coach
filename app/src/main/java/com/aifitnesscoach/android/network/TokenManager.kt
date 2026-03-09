package com.aifitnesscoach.android.network

import android.content.Context

class TokenManager(context: Context) {

    private val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("jwt", token).apply()
    }

    fun getToken(): String? {
        return prefs.getString("jwt", null)
    }

    fun clearToken() {
        prefs.edit().clear().apply()
    }
}