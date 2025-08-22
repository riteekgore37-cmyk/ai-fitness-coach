package com.aifitnesscoach.android.ui.home.ui.plan.domain.models.allExercises

import com.aifitnesscoach.android.network.models.BaseResponse

data class ExercisesResponse(
    val `data`: List<Data>,
    val meta: Meta,
) : BaseResponse()