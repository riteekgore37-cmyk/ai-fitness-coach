package com.aifitnesscoach.android.ui.onboarding.utils.UserPref

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.aifitnesscoach.android.ui.onboarding.models.LoginData

object UserPrefUtil {

    private const val PREF_FILE_NAME = "myPreferences"
    private const val DATA_KEY = "data_key"
    private const val LOGGED_IN_KEY = "logged_in_status"

    private val gson = Gson()

    fun saveUserData(context: Context, data: LoginData?) {
        val jsonData = gson.toJson(data)

        context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(DATA_KEY, jsonData)
            .apply()
    }

    fun getUserData(context: Context): LoginData? {

        val jsonData = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
            .getString(DATA_KEY, null) ?: return null

        val type = object : TypeToken<LoginData>() {}.type

        return gson.fromJson(jsonData, type)
    }

    fun setUserLoggedIn(context: Context, isLoggedIn: Boolean) {
        context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(LOGGED_IN_KEY, isLoggedIn)
            .apply()
    }

    fun isUserLoggedIn(context: Context): Boolean {
        return context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
            .getBoolean(LOGGED_IN_KEY, false)
    }

    fun logout(context: Context) {
        context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
            .edit()
            .remove(DATA_KEY)
            .putBoolean(LOGGED_IN_KEY, false)
            .apply()
    }
}