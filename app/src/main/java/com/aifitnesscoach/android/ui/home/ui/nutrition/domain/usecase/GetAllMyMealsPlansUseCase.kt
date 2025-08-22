package com.aifitnesscoach.android.ui.home.ui.nutrition.domain.usecase

import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.NutritionRepository
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.models.all_meals_plan.AllMealsPlansResponse

class GetAllMyMealsPlansUseCase(private var nutritionRepository: NutritionRepository) {
    suspend fun invoke(
        token: String
    ): ApiResult<AllMealsPlansResponse> {
        return nutritionRepository.getAllMealsPlans(token)
    }
}