package com.aifitnesscoach.android.ui.home.ui.plan.domain.usecase

import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.ui.home.ui.plan.domain.MyPlanRepository
import com.aifitnesscoach.android.ui.home.ui.plan.domain.models.PlanPageResponse

class PlanPageUseCase(private val planRepository: MyPlanRepository) {
    suspend fun invoke(workoutId: String, token: String): ApiResult<PlanPageResponse> {
        return planRepository.getMyPlanPage(workoutId, token)
    }
}