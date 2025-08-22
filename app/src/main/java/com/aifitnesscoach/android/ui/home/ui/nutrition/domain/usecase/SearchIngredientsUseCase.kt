package com.aifitnesscoach.android.ui.home.ui.nutrition.domain.usecase

import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.ui.home.ui.nutrition.domain.NutritionRepository
import com.aifitnesscoach.android.ui.home.ui.nutrition.models.ingredients.IngredientsResponse

class SearchIngredientsUseCase(private var nutritionRepository: NutritionRepository) {
    suspend fun invoke(
        token: String, searchTerm: String
    ): ApiResult<IngredientsResponse> {
        return nutritionRepository.searchIngredients(token, searchTerm)
    }
}