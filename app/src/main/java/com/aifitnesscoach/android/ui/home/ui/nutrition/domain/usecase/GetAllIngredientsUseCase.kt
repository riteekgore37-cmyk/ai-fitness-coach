package com.aifitnesscoach.android.ui.home.ui.nutrition.domain.usecase

import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.NutritionRepository
import com.aifitnesscoach.android.ui.home.ui.nutrition.models.ingredients.IngredientsResponse

class GetAllIngredientsUseCase(private var nutritionRepository: NutritionRepository) {
    suspend fun invoke(
        token: String, page: Int, limit: Int
    ): ApiResult<IngredientsResponse> {
        return nutritionRepository.getAllIngredients(token, page, limit)
    }
}