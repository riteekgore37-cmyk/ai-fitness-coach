package com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.today_intake

import com.aifitnesscoach.android.network.models.BaseResponse

data class TodayInTakeResponse(
    val `data`: Data,
) : BaseResponse()