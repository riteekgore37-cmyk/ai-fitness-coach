package com.aifitnesscoach.android.ui.home.ui.home.domain.usecase

import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.ui.home.ui.home.domain.HomeRepository
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.today_intake.TodayInTakeResponse

class TodayInTakeUseCase(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(token: String): ApiResult<TodayInTakeResponse> {
        return homeRepository.getTodayInTake(token)
    }
}