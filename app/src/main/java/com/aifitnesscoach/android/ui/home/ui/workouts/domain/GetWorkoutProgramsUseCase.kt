package com.aifitnesscoach.android.ui.home.ui.workouts.domain

import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.ui.home.ui.workouts.models.workout_programs.WorkoutProgramsResponse

class GetWorkoutProgramsUseCase(private var workoutsProgramsRepo: WorkoutsRepo) {
    suspend fun invoke(token: String): ApiResult<WorkoutProgramsResponse> {
        return workoutsProgramsRepo.getWorkoutsPrograms(token)
    }
}