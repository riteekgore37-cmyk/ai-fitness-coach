package com.aifitnesscoach.android.ui.onboarding.utils.UserPref

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.aifitnesscoach.android.ui.onboarding.models.Session
import androidx.core.content.edit

object UserPrefUtil {

    private const val PREF_FILE_NAME = "myPreferences"
    private const val SESSION_KEY = "session_key"
    private const val LOGGED_IN_KEY = "logged_in_status"

    private val gson = Gson()

    fun saveSession(context: Context, session: Session) {
        val jsonData = gson.toJson(session)
        context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
            .edit {
                putString(SESSION_KEY, jsonData)
            }
    }

    fun getUserData(context: Context): Session? {
        val jsonData = context
            .getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
            .getString(SESSION_KEY, null) ?: return null
        val type = object : TypeToken<Session>() {}.type
        return gson.fromJson(jsonData, type)
    }

    fun setUserLoggedIn(context: Context, isLoggedIn: Boolean) {
        context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
            .edit {
                putBoolean(LOGGED_IN_KEY, isLoggedIn)
            }
    }

    fun isUserLoggedIn(context: Context): Boolean {
        return context
            .getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
            .getBoolean(LOGGED_IN_KEY, false)
    }

    fun logout(context: Context) {
        context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
            .edit {
                clear()
            }
    }
}