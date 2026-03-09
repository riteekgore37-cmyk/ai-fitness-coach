package com.aifitnesscoach.android.ui.onboarding.models

import com.aifitnesscoach.android.network.models.BaseResponse

data class LoginResponse(
    val data: LoginData
) : BaseResponse()