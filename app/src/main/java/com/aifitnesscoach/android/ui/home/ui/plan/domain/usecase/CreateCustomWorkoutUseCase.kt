package com.aifitnesscoach.android.ui.home.ui.plan.domain.usecase

import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.ui.home.ui.plan.domain.MyPlanRepository
import com.aifitnesscoach.android.ui.home.ui.plan.domain.models.CreateCustomWorkoutRequest
import com.aifitnesscoach.android.ui.home.ui.plan.domain.models.customworkout.create.CreateCustomWorkoutResponse

class CreateCustomWorkoutUseCase(private var planRepository: MyPlanRepository) {
    suspend fun invoke(
        token: String, createCustomWorkoutRequest: CreateCustomWorkoutRequest
    ): ApiResult<CreateCustomWorkoutResponse> {
        return planRepository.createCustomWorkout(token, createCustomWorkoutRequest)
    }
}