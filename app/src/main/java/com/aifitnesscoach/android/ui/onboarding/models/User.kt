package com.aifitnesscoach.android.ui.onboarding.models

data class User(
    val age: Int,
    val email: String,
    val fitness_level: String,
    val gender: String,
    val height: Int,
    val id: String,
    val injuries: List<String>,
    val name: String,
    val preferences: Preferences,
    val role: String,
    val weight: Int
)