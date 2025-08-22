package com.aifitnesscoach.android.ui.home.ui.nutrition.domain.usecase

import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.NutritionRepository
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.daily_goals.DailyGoalsResponse

class GetDailyGoalsUseCase(private var nutritionRepository: NutritionRepository) {
    suspend fun invoke(
        token: String
    ): ApiResult<DailyGoalsResponse> {
        return nutritionRepository.getDailyGoals(token)
    }
}