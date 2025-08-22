package com.aifitnesscoach.android.ui.workout.domain.repo

import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.network.models.BaseResponse

interface WorkoutRepository {
    suspend fun markDoneWorkout(
        myWorkoutId: String, week: Int, day: Int, token: String
    ): ApiResult<BaseResponse>
}
