package com.aifitnesscoach.android.network.models

import com.google.gson.annotations.SerializedName

data class GetProfileResponse(
    val success: Boolean,
    val user: UserProfile
)

data class UserProfile(

    @SerializedName("_id")
    val id: String?,

    val name: String?,
    val email: String?,
    val dob: String?,
    val gender: String?,
    val height: Int?,
    val weight: Int?,
    val fitness_level: String?,

    @SerializedName("fitness_goal")
    val goal: String?,

    val bmi: Double?,
    val daily_calories: Int?
)