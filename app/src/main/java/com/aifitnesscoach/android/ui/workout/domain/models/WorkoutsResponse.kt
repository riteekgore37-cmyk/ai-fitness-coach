package com.aifitnesscoach.android.ui.workout.domain.models

import com.aifitnesscoach.android.network.models.BaseResponse

data class WorkoutsResponse(
    val `data`: Data
) : BaseResponse()