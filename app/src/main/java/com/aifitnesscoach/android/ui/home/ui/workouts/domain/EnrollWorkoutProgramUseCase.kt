package com.aifitnesscoach.android.ui.home.ui.workouts.domain

import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.network.models.BaseResponse
import com.aifitnesscoach.android.ui.home.ui.workouts.models.Workout

class EnrollWorkoutProgramUseCase(private var workoutsProgramsRepo: WorkoutsRepo) {
    suspend fun invoke(token: String, workoutId: Workout): ApiResult<BaseResponse> {
        return workoutsProgramsRepo.enrollWorkoutProgram(token, workoutId)
    }
}