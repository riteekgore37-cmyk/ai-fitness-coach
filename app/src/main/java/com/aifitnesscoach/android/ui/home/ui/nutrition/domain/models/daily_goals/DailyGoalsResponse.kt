package com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.daily_goals

import com.aifitnesscoach.android.network.models.BaseResponse

data class DailyGoalsResponse(
    val `data`: Data,
) : BaseResponse()