package com.aifitnesscoach.android.ui.home.ui.plan.domain.usecase

import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.ui.home.ui.plan.domain.MyPlanRepository
import com.aifitnesscoach.android.ui.home.ui.plan.domain.models.customworkout.CustomWorkoutResponse

class GetCustomWorkoutUseCase(private var planRepository: MyPlanRepository) {
    suspend fun invoke(token: String): ApiResult<CustomWorkoutResponse> {
        return planRepository.getCustomWorkouts(token)
    }
}