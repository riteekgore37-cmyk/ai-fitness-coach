package com.aifitnesscoach.android.ui.home.ui.plan.domain

import com.aifitnesscoach.android.network.ApiResult
import com.aifitnesscoach.android.ui.home.ui.plan.domain.models.CreateCustomWorkoutRequest
import com.aifitnesscoach.android.ui.home.ui.plan.domain.models.PlanPageResponse
import com.aifitnesscoach.android.ui.home.ui.plan.domain.models.allExercises.ExercisesResponse
import com.aifitnesscoach.android.ui.home.ui.plan.domain.models.customworkout.CustomWorkoutResponse
import com.aifitnesscoach.android.ui.home.ui.plan.domain.models.customworkout.create.CreateCustomWorkoutResponse

interface MyPlanRepository {

    suspend fun getMyPlanPage(workoutId: String, token: String): ApiResult<PlanPageResponse>
    suspend fun getCustomWorkouts(token: String): ApiResult<CustomWorkoutResponse>

    suspend fun getExercises(
        token: String, filterName: String, filterVal: String, page: Int, limit: Int
    ): ApiResult<ExercisesResponse>

    suspend fun getSearchExercises(
        token: String, searchTerm: String, filter: String
    ): ApiResult<ExercisesResponse>

    suspend fun createCustomWorkout(
        token: String, createCustomWorkoutRequest: CreateCustomWorkoutRequest
    ): ApiResult<CreateCustomWorkoutResponse>

}