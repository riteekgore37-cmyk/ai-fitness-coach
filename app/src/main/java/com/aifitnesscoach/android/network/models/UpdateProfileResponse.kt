package com.aifitnesscoach.android.network.models

data class UpdateProfileResponse(
    val success: Boolean,
    val message: String,
    val user: UserProfile
)

data class UserProfile(
    val _id: String,
    val name: String,
    val email: String,
    val dob: String?,
    val gender: String?,
    val height: Int?,
    val weight: Int?,
    val fitness_level: String?
)