package com.aifitnesscoach.android.ui.workout.domain.usecase

import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.network.models.BaseResponse
import com.aifitnesscoach.android.ui.workout.domain.repo.WorkoutRepository

class MarkWorkoutDoneUseCase(private val workoutRepository: WorkoutRepository) {

    suspend operator fun invoke(
        myWorkoutId: String, week: Int, day: Int, token: String
    ): ApiResult<BaseResponse> {
        return workoutRepository.markDoneWorkout(myWorkoutId, week, day, token)
    }
}
