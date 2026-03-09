package com.aifitnesscoach.android.network.models

data class UpdateProfileRequest(
    val dob: String?,
    val gender: String?,
    val height: Int?,
    val weight: Int?,
    val fitness_level: String?,
    val injuries: List<String>?,
    val preferences: Preferences?
)

data class Preferences(
    val fitness_goal: String?,
    val target_weight: Int?,
    val workout_place: String?,
    val preferred_equipment: List<String>?
)