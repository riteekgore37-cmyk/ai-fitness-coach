package com.aifitnesscoach.android.ui.home.ui.plan.domain.usecase

import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.ui.home.ui.plan.domain.MyPlanRepository
import com.aifitnesscoach.android.ui.home.ui.plan.domain.models.allExercises.ExercisesResponse

class SearchExercisesUseCase(private val planRepository: MyPlanRepository) {
    suspend fun invoke(
        token: String, searchTerm: String, filter: String
    ): ApiResult<ExercisesResponse> {
        return planRepository.getSearchExercises(token, searchTerm, filter)
    }
}