package com.aifitnesscoach.android.ui.home.ui.workouts.domain

import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.network.models.BaseResponse
import com.aifitnesscoach.android.ui.home.ui.workouts.models.Workout
import com.aifitnesscoach.android.ui.home.ui.workouts.models.workout_programs.WorkoutProgramsResponse

interface WorkoutsRepo {

    suspend fun getWorkoutsPrograms(token: String): ApiResult<WorkoutProgramsResponse>
    suspend fun enrollWorkoutProgram(token: String, workoutId: Workout): ApiResult<BaseResponse>


}