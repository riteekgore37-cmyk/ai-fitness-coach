package com.aifitnesscoach.android.network.models

data class BaseErrorResponse(
    val code: Int,
    val errors: List<String>,
    val success: Boolean
)