package com.aifitnesscoach.android.ui.home.ui.nutrition.domain.usecase

import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.NutritionRepository
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.today_meals.TodayMealsResponse

class GetTodayMealUseCase(private var nutritionRepository: NutritionRepository) {
    suspend fun invoke(
        token: String
    ): ApiResult<TodayMealsResponse> {
        return nutritionRepository.getTodayMeal(token)
    }
}