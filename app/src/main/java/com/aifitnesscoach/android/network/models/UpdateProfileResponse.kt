package com.aifitnesscoach.android.network.models

data class UpdateProfileResponse(
    val success: Boolean,
    val message: String,
    val user: UserProfile
)