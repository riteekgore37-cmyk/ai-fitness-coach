package com.aifitnesscoach.android.ui.onboarding.models

import com.google.gson.annotations.SerializedName

data class LoginData(
    val token: String,
    val user: User
)

data class LoginResponse(
    @SerializedName("status") val status: Int = 0,
    @SerializedName("message") val message: String = "",
    @SerializedName("data") val loginData: LoginData? = null
) {
    val success: Boolean get() = status == 200 && loginData != null
    val token: String get() = loginData?.token ?: ""
    val user: User? get() = loginData?.user
}